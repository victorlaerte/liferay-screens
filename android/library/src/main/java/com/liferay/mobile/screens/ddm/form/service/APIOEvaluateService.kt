package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.exception.ThingWithoutOperationException
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer

/**
 * @author Paulo Cruz
 */
class APIOEvaluateService : IEvaluateService, BaseAPIOService() {

    private val operationId = "evaluate-context"

    fun evaluateContext(formThing: Thing, fields: MutableList<Field<*>>,
                        onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

        formThing.getOperation(operationId)?.let { operation ->
            evaluateContext(formThing.id, operation.id, fields, onSuccess, onError)
        } ?: onError(ThingWithoutOperationException(formThing.id, operationId))
    }

    override fun evaluateContext(thingId: String, operationId: String,
                                 fields: MutableList<Field<*>>, onSuccess: (Thing) -> Unit,
                                 onError: (Exception) -> Unit) {

        apioConsumer.performOperation(thingId, operationId, {
            mapOf(
                Pair("fieldValues", FieldValueSerializer.serialize(fields))
            )
        }, onSuccess, onError)
    }
}