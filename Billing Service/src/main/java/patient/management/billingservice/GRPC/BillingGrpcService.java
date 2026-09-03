package patient.management.billingservice.GRPC;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver) {

        log.info("createBillingAccount request received {}", billingRequest.toString());

        // Business logic - e.g save to database, perform calculates etc

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
