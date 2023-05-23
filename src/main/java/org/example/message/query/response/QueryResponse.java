package org.example.message.query.response;

import org.example.message.Message;
import org.example.message.query.Query;

public interface QueryResponse extends Message {
    Query getInitialQuery();
}
