package de.hdm.ast.drive;

import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.sqladmin.SQLAdminScopes;

// ...

AppIdentityCredential credential =
    new AppIdentityCredential(SQLAdminScopes.SQLSERVICE_ADMIN);



public class Authorization {

}
