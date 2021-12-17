if exist jre ( 
    set javaDir=jre\bin\
)

%javaDir%java.exe -Xmx1024m -cp "classes;lib/*;conf" -Druv.runtime.mode=desktop ruv.tools.SignTransactionJSON
