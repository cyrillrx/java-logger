package com.cyrillrx.tracker.context

import java.util.HashMap
import java.util.HashSet

/**
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
class TrackerContext {

    var app: App? = null
        private set
    var user: User? = null
        private set
    var device: Device? = null
        private set
    var userAgent: String? = null
        private set

    private val listeners: MutableSet<UserChangedListener>
    private val customAttributes: MutableMap<String, String>

    init {
        listeners = HashSet()
        customAttributes = HashMap()
    }

    fun setApp(app: App) = apply {
        this.app = app
    }

    fun setUser(user: User?) = apply {
        this.user = user
        for (listener in listeners) {
            listener.onUserChanged(user)
        }
    }

    fun setDevice(device: Device?) = apply {
        this.device = device
    }

    fun setUserAgent(userAgent: String?) = apply {
        this.userAgent = userAgent
    }

    /**
     * Adds a listener that will be notified when [User] is updated.
     */
    fun addListener(listener: UserChangedListener) = apply {
        listeners.add(listener)
    }

    fun getCustomAttributes(): Map<String, String> = customAttributes

    fun putCustomAttribute(key: String, value: String) = apply {
        customAttributes[key] = value
    }

    fun putCustomAttributes(values: Map<String, String>) = apply {
        customAttributes.putAll(values)
    }

    class App(val name: String, val version: String)

    class User {
        private var id: String? = null
        private var name: String? = null
        private var email: String? = null

        fun setId(id: String) = apply {
            this.id = id
        }

        fun setName(name: String) = apply {
            this.name = name
        }

        fun setEmail(email: String) = apply {
            this.email = email
        }
    }

    class Device {
        var os: String? = null
        var osVersion: String? = null
        var brand: String? = null
        var manufacturer: String? = null
        var model: String? = null
        var serial: String? = null
        var display: String? = null
        var connectivity: Connectivity? = null
    }

    interface UserChangedListener {
        fun onUserChanged(user: User?)
    }
}