rmdir /s /q wallet
call cordova create wallet org.ruv.mobile.wallet "RuvChain Mobile Wallet" --template ..\..\html
cd wallet
rmdir /s /q icons
xcopy /y/i/s ..\..\icons icons
rmdir /s /q plugins
xcopy /y/i/s ..\..\plugins plugins
call cordova platform add android@6.4.0
xcopy /y/i/s ..\..\platforms platforms
cd ..
