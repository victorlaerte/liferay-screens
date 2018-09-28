package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.ApioConsumer
import com.liferay.apio.consumer.authenticator.BasicAuthenticator
import com.liferay.mobile.screens.context.SessionContext

/**
 * @author Paulo Cruz
 */
abstract class BaseAPIOService {
    val apioConsumer: ApioConsumer = ApioConsumer()

    init {
        val basicCredentials = SessionContext.getCredentialsFromCurrentSession()
        apioConsumer.setAuthenticator(BasicAuthenticator(basicCredentials))
    }
}