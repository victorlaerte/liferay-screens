package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.ApioConsumer
import com.liferay.apio.consumer.exception.ThingWithoutOperationException
import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Thing

/**
 * @author Paulo Cruz
 */
class APIOFetchLatestDraftService : IFetchLatestDraftService, BaseAPIOService() {

    private val operationId = "fetch-latest-draft"

    override fun fetchLatestDraft(formThing: Thing, onSuccess: (Thing) -> Unit,
                                  onError: (Exception) -> Unit) {

        formThing.getOperation(operationId)?.let {
            performFetch(formThing, it, onSuccess, onError)
        } ?: onError(ThingWithoutOperationException(formThing.id, operationId))
    }

    private fun performFetch(thing: Thing, operation: Operation, onSuccess: (Thing) -> Unit,
                             onError: (Exception) -> Unit) {

        ApioConsumer.performOperation(thing.id, operation.id, { emptyMap() }, onSuccess, onError)
    }

}