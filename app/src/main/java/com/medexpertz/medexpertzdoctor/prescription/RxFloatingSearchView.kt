package com.medexpertz.medexpertzdoctor.prescription

import com.arlib.floatingsearchview.FloatingSearchView
import com.jakewharton.rxbinding2.InitialValueObservable

inline fun FloatingSearchView.queryChanges(minQueryLimit: Int = 1): InitialValueObservable<CharSequence> = RxFloatingSearchView.queryChanges(this, minQueryLimit)