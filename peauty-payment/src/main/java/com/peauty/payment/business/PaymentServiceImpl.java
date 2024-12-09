package com.peauty.payment.business;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.payment.business.dto.CompletePaymentCommand;
import com.peauty.payment.business.dto.CompletePaymentResult;
import com.peauty.payment.business.dto.OrderCommand;
import com.peauty.payment.business.dto.OrderResult;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentPort paymentPort;
    private final IamportClient iamportClient;

    // TODO. 1. 사전 검증
    // TODO: 2. 사후 검증
    //      - 포트원에서 결제 정보 가져오기

    @Transactional
    @Override
    public OrderResult saveOrder(Long userId, Long processId, Long threadId, OrderCommand command) {
        // TODO: 유저 검증
        // TODO: 요청서 & 견적서 정보 검증 (요청서, 견적서가 DB에 있는지 검증)
        String uuid = UUID.randomUUID().toString();
        LocalDateTime orderDate = LocalDateTime.now();
        Payment paymentToSave = Payment.initializePayment(userId, command);

        Order orderToSave = command.toDomain(
                userId, processId, threadId,
                uuid, orderDate, paymentToSave);

        // 예약금 변환을 thread에서 가져와서 변환을 해야함 -> 그 금액과 같은지 확인해야 함
        orderToSave.transferReservationCost(orderToSave.getPrice());
        Order savedOrder = paymentPort.save(orderToSave);
        return OrderResult.from(savedOrder);
    }

    @Transactional
    @Override
    public CompletePaymentResult completePayment(Long userId, Long threadId, Long processId,
                                                 CompletePaymentCommand command) {
        Order order = paymentPort.getOrder(userId);
        Payment savedPayment = order.getPayment();
        try {
            IamportResponse<com.siot.IamportRestClient.response.Payment>
                    iamportResponse = iamportClient.paymentByImpUid(command.uuid());

            if (!"paid".equals(iamportResponse.getResponse().getStatus())) {
                paymentPort.orderDelete(order);
                paymentPort.paymentDelete(order);
                throw new RuntimeException("결제 미완료");
            }

            int actualAmount = iamportResponse.getResponse().getAmount().intValue();
            Integer expectedAmount = order.getPrice();
            if (!expectedAmount.equals(actualAmount)) {
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(actualAmount)));
                throw new RuntimeException("결제 금액 위변조");
            }

            // order.getPayment().changePaymentBySuccess(PaymentStatus.OK, iamportResponse.getResponse().getImpUid());

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // TODO 2. 포트원의 정보와 내가 저장한 값이 같은지 확인하기
        // TODO 3. 확인 후, 포트원에 결제 완료 API 보내기
        // TODO 4. 값을 받고 나서 상태를 바꿔주면서 클라이언트로 전송하기
        return null;
    }

}
