# Plugin Layout
```
[root@localhost azkaban-solo-server-3.46.0]# tree plugins/viewer/
plugins/viewer/
└── usermanager
    ├── conf
    │   └── plugin.properties
    └── lib
        └── azkaban-jdbc-usermanager-1.0.0.jar

3 directories, 2 files
```


# JdbcUserManager
```
 cp azkaban-jdbc-usermanager-1.0.0.jar $AZKABAN_HOME/lib
```

# conf/azkaban.properties
```
user.manager.class=azkaban.user.JdbcUserManager
```