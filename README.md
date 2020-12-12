![QKSMS](https://github.com/axel7083/qksms/blob/master/Screenshot.png)

# QKSMS

This version of the [QKSMS](https://github.com/moezbhatti/qksms) [stock messaging app](https://github.com/android/platform_packages_apps_mms) on Android is a modified one adding RSA encryption for SMS.

# How to use

To be able to have an encrypted communication over sms, you need to have two device with both this version of the application installed. 

1. Open the conversation and use the three-dots to access the options menu.
2. Select `Scan a public RSA Key`
3. Grant the CAMERA permission (if you did previously)
4. On the other device, on the startup page open the sidebar menu
5. Select `Show my public key`
6. Scan the public key with the first device
7. Repeat the operation on the other way
8. Now you can send SMS, they will be encrypted

## License

QKSMS is released under the **The GNU General Public License v3.0 (GPLv3)**, which can be found in the `LICENSE` file in the root of this project.
