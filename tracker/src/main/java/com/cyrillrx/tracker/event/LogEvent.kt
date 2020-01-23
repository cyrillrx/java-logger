package com.cyrillrx.tracker.event

import com.cyrillrx.tracker.context.TrackerContext
import com.cyrillrx.tracker.utils.requireNotBlank
import java.util.HashMap

/**
 * @author Cyril Leroux
 *         Created on 25/04/2016.
 */
class LogEvent protected constructor(
    /** Global context. Not directly related to the event.  */
    val context: TrackerContext,
    val category: String,
    val name: String,
    /** Source of the event (class name, or ui screen)  */
    val source: String? = null
) {

    var severity = 0
        protected set
    var tag: String? = null
        protected set
    var message: String? = null
        protected set
    var screen: String? = null
        protected set
    var screenExecId: String? = null
        protected set

    class Builder {
        private var context: TrackerContext? = null
        private var category: String? = null
        private var name: String? = null
        /** Source of the event (class name, or ui screen)  */
        private var source: String? = null

        private val customAttributes: HashMap<String, Any> = HashMap()

        fun build(): LogEvent {
            val context = requireNotNull(context) { "TrackerContext is mandatory. Was null." }

            val category = requireNotBlank(category) { "Category is mandatory." }

            val name = requireNotBlank(name) { "Event name is mandatory." }

            return LogEvent(context, category, name, source)
        }

        fun setContext(context: TrackerContext) = apply {
            this.context = context
        }

        fun setCategory(category: String) = apply {
            this.category = category
        }

        fun setName(name: String) = apply {
            this.name = name
        }

        fun setSource(source: String) = apply {
            this.source = source
        }

        fun putCustomAttribute(key: String, value: Any) = apply {
            this.customAttributes[key] = value
        }

        fun <T : Any> putCustomAttributes(values: Map<String, T>) = apply {
            this.customAttributes.putAll(values)
        }
    }
}