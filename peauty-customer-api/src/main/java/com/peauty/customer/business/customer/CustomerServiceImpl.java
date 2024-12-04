package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerPort customerPort;
    private final InternalPort internalPort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long customerId, MultipartFile file) {
        Customer customer = customerPort.getByCustomerIdWithoutPuppies(customerId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        customer.updateProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(customerPort.save(customer));
    }

    @Override
    public GetCustomerProfileResult getCustomerProfile(Long customerId) {
        Customer customer = customerPort.getByCustomerIdWithoutPuppies(customerId);
        return GetCustomerProfileResult.from(customer);
    }

    @Override
    @Transactional
    public UpdateCustomerProfileResult updateCustomerProfile(Long customerId, UpdateCustomerProfileCommand command) {
        Customer customerToUpdate = customerPort.getByCustomerIdWithoutPuppies(customerId);
        customerToUpdate.updateName(command.name());
        customerToUpdate.updatePhoneNumber(command.phoneNumber());
        customerToUpdate.updateNickname(command.nickname());
        customerToUpdate.updateProfileImageUrl(command.profileImageUrl());
        customerToUpdate.updateAddress(command.address());
        Customer updatedCustomer = customerPort.save(customerToUpdate);
        return UpdateCustomerProfileResult.from(updatedCustomer);
    }

    @Override
    public void checkCustomerNicknameDuplicated(String nickname) {
        customerPort.checkCustomerNicknameDuplicated(nickname);
    }

/*    @Override
    public GetAroundDesignersResult getAroundDesigners(Long customerId) {
        // 고객 정보 가져오기
        Customer customer = customerPort.getCustomerById(customerId);
        // 고객 주소와 같은 디자이너 조회
        List<Designer> designers = customerPort.findDesignersByAddress(customer.getAddress());
        // 디자이너들의 작업공간 정보 가져오기
        List<Workspace> workspaces = designers.stream()
                .map(designer -> customerPort.getWorkspaceByDesignerId(designer.getDesignerId()))
                .toList();

        return GetAroundDesignersResult.from(customer, designers, workspaces);
    }*/

/*  TEST
    @Override
    public GetAroundWorkspacesResult getAroundWorkspaces(Long customerId) {
        // 고객 정보 조회와 상위 주소를 추출
        Customer customer = customerPort.getCustomerById(customerId);
        String customerBaseAddress = extractBaseAddress(customer.getAddress());
        // 고객의 상위 주소와 일치하는 미용실을 조회
        List<Workspace> workspaces = customerPort.findAllWorkspaceByAddress(customerBaseAddress);
        // 일치하는 미용실과 디자이너의 정보를 매핑
        List<GetAroundWorkspacesResult.WorkspaceWithDesigner> workspaceWithDesigners = workspaces.stream()
                .map(workspace -> {
                    Designer designer = customerPort.findDesignerById(workspace.getDesignerId());
                    return new GetAroundWorkspacesResult.WorkspaceWithDesigner(
                            workspace.getWorkspaceId(),
                            workspace.getWorkspaceName(),
                            workspace.getAddress(),
                            workspace.getAddressDetail(),
                            workspace.getBannerImageUrl(),
                            workspace.getReviewCount(),
                            workspace.getReviewRating(),
                            designer.getName(),
                            designer.getYearOfExperience()
                    );
                })
                .toList();

        return new GetAroundWorkspacesResult(customer.getCustomerId(), customer.getAddress(), workspaceWithDesigners);
    }*/
    @Override
    public GetAroundWorkspacesResult getAroundWorkspaces(Long customerId) {
        // 고객 정보 조회
        Customer customer = customerPort.getCustomerById(customerId);
        // OO시 OO구 까지 추출
        String customerBaseAddress = extractBaseAddress(customer.getAddress());
        // 고객의 상위주소와 맞는 미용실 전체 조회
        List<Workspace> workspaces = customerPort.findAllWorkspaceByAddress(customerBaseAddress);
        // 각 미용실에 해당하는 디자이너 정보를 매핑
        List<GetAroundWorkspaceResult> workspaceResults = workspaces.stream()
                .map(workspace -> {
                    // 미용실을 소유한 디자이너 조회
                    Designer designer = customerPort.findDesignerById(workspace.getDesignerId());
                    return GetAroundWorkspaceResult.from(workspace, designer);
                })
                .toList();

        return GetAroundWorkspacesResult.from(customer, workspaceResults);
    }

    private String extractBaseAddress(String address) {
        // 어절(띄어쓰기)로 파싱.
        // 서울 강남구 대치동 -> 서울 강남구
        String[] addressParts = address.split(" ");
        if (addressParts.length < 2) {
        // "" or "서울시"
            throw new PeautyException(PeautyResponseCode.INVALID_ADDRESS_FORMAT);
        }
        // 상위 주소만 딱 반환해버리기
        return addressParts[0] + " " + addressParts[1];
    }



}
