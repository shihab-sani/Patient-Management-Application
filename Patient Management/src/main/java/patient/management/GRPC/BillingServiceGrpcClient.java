package patient.management.GRPC;

import billing.BillRequest;
import billing.BillResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort) {

        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillResponse createBillingAccount(String patientId, String name,
                                             String email) {

        BillRequest request = BillRequest.newBuilder().setPatientId(patientId)
                .setName(name).setEmail(email).build();

        BillResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }
}
