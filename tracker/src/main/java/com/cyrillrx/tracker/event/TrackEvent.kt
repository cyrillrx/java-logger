package com.cyrillrx.tracker.event

import com.cyrillrx.tracker.context.TrackerContext
import com.cyrillrx.tracker.utils.requireNotBlank

/**
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
open class TrackEvent protected constructor(
    val category: String,
    val name: String,
    val customAttributes: HashMap<String, Any>,
    /** Source of the event (class name, or ui screen)  */
    val source: String? = null
) {

    val createdAt: Long = System.currentTimeMillis()

    var sentAt: Long = System.currentTimeMillis()

    /** Global context. Not directly related to the event.  */
    lateinit var context: TrackerContext

    @JvmOverloads
    fun updateSentAt(sentAt: Long = System.currentTimeMillis()) {
        this.sentAt = sentAt
    }

    fun isValid(): Boolean = try {
        require(::context.isInitialized) { "TrackerContext is mandatory but was not set." }
        require(sentAt > createdAt) { "sentAt was not properly updated." }
        true
    } catch (exception: Exception) {
        false
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getCustomAttribute(key: String?): T? = customAttributes[key] as? T

    class Builder {
        private var category: String? = null
        private var name: String? = null
        /** Source of the event (class name, or ui screen)  */
        private var source: String? = null

        private val customAttributes: HashMap<String, Any> = HashMap()

        fun build(): TrackEvent {

            val category = requireNotBlank(category) { "Category is mandatory." }

            val name = requireNotBlank(name) { "Event name is mandatory." }

            return TrackEvent(category, name, customAttributes, source)
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