package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.*;
import com.peauty.designer.business.internal.InternalPort;
import com.peauty.designer.business.shop.WorkspacePort;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.License;
import com.peauty.domain.designer.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerServiceImpl implements DesignerService {

    private final DesignerPort designerPort;
    private final InternalPort internalPort;
    private final WorkspacePort workspacePort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long designerId, MultipartFile file) {
        Designer designer = designerPort.getByDesignerId(designerId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        designer.updateProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(designerPort.save(designer));
    }

    @Override
    public GetDesignerProfileResult getDesignerProfile(Long designerId) {
        Designer designer = designerPort.getByDesignerId(designerId);
        return GetDesignerProfileResult.from(designer);
    }

    @Override
    @Transactional
    public UpdateDesignerProfileResult updateDesignerProfile(Long designerId, UpdateDesignerProfileCommand command) {
        Designer designerToUpdate = designerPort.getByDesignerId(designerId);
        designerToUpdate.updateName(command.name());
        designerToUpdate.updatePhoneNumber(command.phoneNumber());
        designerToUpdate.updateNickname(command.nickname());
        designerToUpdate.updateProfileImageUrl(command.profileImageUrl());
        designerToUpdate.updateAddress(command.address());
        designerToUpdate.updateEmail(command.email());
        Designer updatedDesigner = designerPort.save(designerToUpdate);
        return UpdateDesignerProfileResult.from(updatedDesigner);
    }

    @Override
    public void checkDesignerNicknameDuplicated(String nickname) {
        designerPort.checkCustomerNicknameDuplicated(nickname);
    }

    @Override
    public CreateDesignerWorkspaceResult createDesignerWorkspace(Long userId, CreateDesignerWorkspaceCommand command) {
        Designer designerToCreate = designerPort.getByDesignerId(userId);
        Workspace workspaceToCreate = CreateDesignerWorkspaceCommand.toWorkspace(command);
        List<License> licenseToCreate = CreateDesignerWorkspaceCommand.toLicense(command);

        designerToCreate.updateYearOfExperience(command.yearOfExperience());
        designerToCreate.updateLicenses(licenseToCreate);

        Designer designer = designerPort.save(designerToCreate);
        Workspace workspace = workspacePort.save(workspaceToCreate, designer.getDesignerId());

        return CreateDesignerWorkspaceResult.from(designer, workspace);
    }

    @Override
    public GetDesignerWorkspaceResult getDesignerWorkspace(Long userId) {
        Designer designer = designerPort.getAllDesignerDataByDesignerId(userId);
        Workspace workspace = workspacePort.getByDesignerId(userId);

        return GetDesignerWorkspaceResult.from(designer, workspace);
    }
}
