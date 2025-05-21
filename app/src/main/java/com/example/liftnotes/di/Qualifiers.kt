package com.example.liftnotes.di

import javax.inject.Qualifier

class Qualifiers {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApplicationScope
}