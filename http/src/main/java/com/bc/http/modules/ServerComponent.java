package com.bc.http.modules;

import akka.actor.ActorSystem;
import com.bc.actorsystem.modules.ActorSystemModule;
import com.bc.http.resource.NetResource;
import dagger.Component;
import dagger.Module;

@Component (modules = {ActorSystemModule.class})
public interface ServerComponent {

    NetResource getNetResource();
/*
    ActorsResource getActorsResource();

    StatusResource getStatusResource();

    ClusterResource getClusterResource();

    ConfigResource getConfigResource();

    DispatchersResource getDispatchersResource();

    HealthResource getHealthResource();

    VersionResource getVersionResource();

    SystemActorStatusResource getSystemActorStatusResource();

    ActorStatusResource getActorStatusResource();

    SystemTreeResource getSystemTreeResource();

    DevResource getDevResource();

    ApiResource getApiResource();

    AuditResource getAuditResource();

    PipelineResource getPipelineResource();

    PipelinesResource getPipelinesResource();

    ScriptResource getScriptResource();

    ScriptVersionResource getScriptVersionResource();

    SubmissionResource getSubmissionResource();

    CredentialResource getCredentialResource();

    FinalFileResource getFinalFileResource();

    ContactResource getContactResource();

    ScriptEngineResource getScriptEngineResource();

    JobResource getJobResource();

    JobsResource getJobsResource();

    JobsHistoryResource getJobsHistoryResource();

    TaskResource getTaskResource();

    TasksResource getTasksResource();

    PipelineAuditResource getPipelineAuditResource();

    ScriptAuditResource getScriptAuditResource();

    ScriptVersionAuditResource getScriptVersionAuditResource();

    ContactAuditResource getContactAuditResource();

    CredentialAuditResource getCredentialAuditResource();

    FinalFileAuditResource getFinalFileAuditResource();

    CommLogResource getCommLogResource();

    CommLogsResource getCommLogsResource();

    ConstraintResource getConstraintResource();

    ScriptsResource getScriptsResource();

    ScriptVersionsResource getScriptVersionsResource();

    ContactsResource getContactsResource();

    CredentialListResource getCredentialListResource();

    FinalFileListResource getFinalFileListResource();

    MetaDataResource getMetaDataResource();

    SpyglassResource getSpyglassResource();
*/
}
