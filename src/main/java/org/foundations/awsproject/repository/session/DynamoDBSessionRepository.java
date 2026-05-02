package org.foundations.awsproject.repository.session;

import org.foundations.awsproject.entities.StudentSession;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Optional;

@Repository
public class DynamoDBSessionRepository {

    private final DynamoDbTable<StudentSession> sessionTable;

    public DynamoDBSessionRepository(DynamoDbEnhancedClient enhancedClient) {
        this.sessionTable = enhancedClient.table("sesiones-alumnos", TableSchema.fromBean(StudentSession.class));
    }

    public void save(StudentSession session) {
        sessionTable.putItem(session);
    }

    public Optional<StudentSession> findBySessionString(String sessionString) {
        return sessionTable.index("sessionString-index")
                .query(QueryConditional.keyEqualTo(Key.builder().partitionValue(sessionString).build()))
                .stream()
                .flatMap(page -> page.items().stream())
                .findFirst();
    }

    public void update(StudentSession session) {
        sessionTable.updateItem(session);
    }
}
