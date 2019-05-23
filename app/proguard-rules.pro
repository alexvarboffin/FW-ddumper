#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Other - D:\android\GitHub\facebook\
#D:\\source\\CallRecorder\\app\\

#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Allow methods with the same signature, except for the return type,
# to get the same obfuscation name.

#-overloadaggressively



#-optimizationpasses 30
#-mergeinterfacesaggressively
#-dontpreverify
#-optimizations !code/simplification/arithmetic
## Put all obfuscated classes into the nameless root package.
#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-repackageclasses 'con'#res

#-useuniqueclassmembernames
#-keeppackagenames doNotKeepAThing
#
## Allow classes and class members to be made public.
#
#-allowaccessmodification
-obfuscationdictionary D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt

#D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt
-classobfuscationdictionary D:\android\GitHub\facebook\proguard\examples\dictionaries\compact.txt
-packageobfuscationdictionary D:\android\GitHub\facebook\proguard\examples\dictionaries\windows.txt


#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-flattenpackagehierarchy 'xxx'#xxxxx


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService


#-dontnote
# OkHttp and Servlet optional dependencies
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.* { *; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.**



#andoidx
-keep class androidx.core.view.* { *; }
-keep class android.app.* { *; }


-dontwarn com.google.appengine.**
-dontwarn javax.servlet.**

# Support classes for compatibility with older API versions

-dontwarn android.support.**
-dontnote android.support.**

-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

-keep class android.support.v7.widget.SearchView { *; }

-printconfiguration config.txt




-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

-assumenosideeffects class android.util.Log{
    public static *** d(...);
    public static *** v(...);
}


#You can remove logging calls with this option in proguard-project.txt:
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
-dontwarn okio.**

#-dontwarn retrofit2.Platform$Java8
#-dontwarn retrofit.Platform$Java8
#-dontwarn retrofit2.Platform$Java8
#-dontwarn rx.internal.util.**

# -keepclassmembers class com.psyberia.sms_regcom.rest.beans.** {
#   *;
# }

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

 #Firebase SDK 2.0.0:
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**

# Only necessary if you downloaded the SDK jar directly instead of from maven.
-keep class com.shaded.fasterxml.jackson.** { *; }




#-keep class com.dropbox.core.** {*;}
#-keep class com.dropbox.** {*;}


-keep class com.google.common.** {*;}
-keep class com.square.picasso.** {*;}
-keep class javax.servlet.http.** {*;}

-keep class okio.Okio.** {*;}


#-keep class com.google.android.gms.auth.** {*;}
#-keep class com.google.android.gms.drive.** {*;}


#GMS
#-keep public class com.google.android.gms.* { public *; }
-keep class com.google.android.gms.** {*;}
-dontwarn com.google.android.gms.**

-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
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


#butterknife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }