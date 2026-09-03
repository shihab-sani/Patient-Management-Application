package patient.management.analyticsservice.Kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvents;

@Slf4j
@Service
public class KafkaConsumer {
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvents patientEvent = PatientEvents.parseFrom(event);
            log.info("Received patient event: [PatientId={}, Email={}, Name={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getEmail(),
                    patientEvent.getName());
        } catch (InvalidProtocolBufferException e) {
            log.error("Failed to parse patient event: {}", e.getMessage());
        }
    }
}
