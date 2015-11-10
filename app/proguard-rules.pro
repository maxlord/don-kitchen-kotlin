# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/max/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn java.lang.invoke.*

-keepattributes *Annotation*
-keepattributes Signature

-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
-keepattributes SourceFile, LineNumberTable


# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# OkHttp
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

# RxJava, Retrofit
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

# RxAndroid
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}



# Validation
-keep class com.mobsandgeeks.saripaar.** {*;}
-keep @com.mobsandgeeks.saripaar.annotation.ValidateUsing class * {*;}

# JodaTime
-dontwarn org.joda.convert.**

-keep class sun.misc.Unsafe { *; }

-keep class org.lucasr.twowayview.** { *; }

# Google Play Services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# AppCompat v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }


# Material Spinner
-dontwarn com.rey.material.**
#-keep class com.rey.material.** { *; }
#-keepclasseswithmembers class com.rey.material.** { *; }

#FlyWay DB
-dontwarn org.flywaydb.core.**

#SqlDroidDriver
-keep class org.sqldroid.**
-keepclassmembers class org.sqldroid.** { *; }

# OrmLite
-keep class com.j256.**
-keep class com.j256.** { *; }
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-dontwarn org.slf4j.**
-dontwarn javax.persistence.**

# Keep the helper class and its constructor
-keep class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
-keepclassmembers class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper {
  public <init>(android.content.Context);
}

# Keep all model classes that are used by OrmLite
# Also keep their field names and the constructor
-keep @com.j256.ormlite.table.DatabaseTable class * {
    @com.j256.ormlite.field.DatabaseField <fields>;
    @com.j256.ormlite.field.ForeignCollectionField <fields>;
    # Add the ormlite field annotations that your model uses here
    <init>();
}

-keepclassmembers class **DateTime {
    <init>(long);
    long getMillis();
}

-keep class ru.zernovozonline.zerno.db.DatabaseHelper.**
-keepclassmembers class ru.zernovozonline.zerno.db.DatabaseHelper.** { *; }
-keep class ru.zernovozonline.zerno.db.dao.**
-keepclassmembers class ru.zernovozonline.zerno.db.dao.** { *; }
-keep class ru.zernovozonline.zerno.db.converter.**
-keepclassmembers class ru.zernovozonline.zerno.db.converter.** { *; }

#Stetho
-keep class com.facebook.stetho.** { *; }
-dontwarn com.facebook.stetho.**
