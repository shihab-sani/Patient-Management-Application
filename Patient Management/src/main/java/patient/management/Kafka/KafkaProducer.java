package patient.management.Kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvents;
import patient.management.ModelClasses.Patient;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendEvent(Patient patient) {

        PatientEvents patientEvent = PatientEvents.newBuilder().
                setPatientId(patient.getId().toString()).
                setEmail(patient.getEmail()).
                setName(patient.getName()).
                setEventType("Patient Created").build();

        try {
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        } catch (Exception e) {
            log.error("Failed to send patient event to Kafka: {}", patientEvent);
        }
    }
}
