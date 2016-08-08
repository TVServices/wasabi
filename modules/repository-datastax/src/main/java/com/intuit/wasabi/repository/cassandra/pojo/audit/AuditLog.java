package com.intuit.wasabi.repository.cassandra.pojo.audit;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Table(name="auditlog")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @PartitionKey(0)
    @Column(name = "application_name")
    String appName;

    @PartitionKey(1)
    Date time;

    @PartitionKey(2)
    @Column(name = "event_id")
    UUID eventId;

    @Column(name = "experiment_id")
    UUID experimentId;

    @Column(name = "experiment_label")
    String  experimentLabel;

    @Column(name = "action")
    String  action;

    @Column(name = "bucket_label")
    String  bucketLabel;

    //TODO: move the user info into a UDT
    @Column(name = "user_firstname")
    String  firstName;

    @Column(name = "user_lastname")
    String  lastName;

    @Column(name = "user_email")
    String  email;

    @Column(name = "user_username")
    String  username;

    @Column(name = "user_userid")
    String  userId;

    @Column(name = "changed_property")
    String  property;

    @Column(name = "property_before")
    String  before;

    @Column(name = "property_after")
    String  after;

}