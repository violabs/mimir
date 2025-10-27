2025-10-27 14:19:39.990 | 2025-10-27T19:19:39.990Z CPU architecture: arm64
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.990Z Linux kernel version: 6.10.14-linuxkit
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.990Z Base image: Red Hat Enterprise Linux 9.6 (Plow)
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.991Z Running as user ID 1001 with primary group 0, and supplementary groups 0
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.991Z Capabilities (bounding set): chown,dac_override,fowner,fsetid,kill,setgid,setuid,setpcap,net_bind_service,net_raw,sys_chroot,mknod,audit_write,setfcap
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.991Z seccomp enforcing mode: filtering
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.991Z Process security attributes: none
2025-10-27 14:19:39.991 | 2025-10-27T19:19:39.991Z Detected 'ext4' volume mounted to /mnt/mqm
2025-10-27 14:19:40.659 | 2025-10-27T19:19:40.659Z Environment variable MQ_ADMIN_PASSWORD is deprecated, use secrets to set the passwords
2025-10-27 14:19:41.166 | 2025-10-27T19:19:41.166Z Environment variable MQ_APP_PASSWORD is deprecated, use secrets to set the passwords
2025-10-27 14:19:41.184 | 2025-10-27T19:19:41.184Z Using queue manager name: QM1
2025-10-27 14:19:41.213 | 2025-10-27T19:19:41.213Z Created directory structure under /var/mqm
2025-10-27 14:19:41.213 | 2025-10-27T19:19:41.213Z Image created: 2025-10-26T14:37:14+00:00
2025-10-27 14:19:41.213 | 2025-10-27T19:19:41.213Z Image tag: ibm-mqadvanced-server-dev:9.4.4.0-arm64
2025-10-27 14:19:41.219 | 2025-10-27T19:19:41.219Z MQ version: 9.4.4.0
2025-10-27 14:19:41.219 | 2025-10-27T19:19:41.219Z MQ level: p944-L251003
2025-10-27 14:19:41.219 | 2025-10-27T19:19:41.219Z MQ license: Developer
2025-10-27 14:19:41.830 | 2025-10-27T19:19:41.829Z FIPS cryptography is not enabled.
2025-10-27 14:19:41.830 | 2025-10-27T19:19:41.829Z Creating queue manager QM1
2025-10-27 14:19:41.830 | 2025-10-27T19:19:41.830Z Starting web server
2025-10-27 14:19:42.539 | 2025-10-27T19:19:42.539Z Created queue manager
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.542Z Removing existing ServiceComponent configuration
2025-10-27 14:19:42.542 | 2025-10-27T19:19:41.943Z AMQ6287I: IBM MQ V9.4.4.0 (p944-L251003). [CommentInsert1(Linux 6.10.14-linuxkit (MQ Linux (ARM 64-bit platform) 64-bit)), CommentInsert2(/opt/mqm (Installation1)), CommentInsert3(9.4.4.0 (p944-L251003))]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.002Z AMQ7374I: Log replay for the queue manager has started at Log Sequence Number (LSN) '<0:0:0:4820> 0x00000000000012d4'. [ArithInsert1(1), ArithInsert2(4820), CommentInsert1(<0:0:0:4820> 0x00000000000012d4), CommentInsert2(2025-10-27T19:19:41.951000), CommentInsert3(S0000000.LOG)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.003Z AMQ7229I: 4 log records accessed on queue manager 'QM1' during the log replay phase. [ArithInsert1(4), CommentInsert1(QM1)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.003Z AMQ7230I: Log replay for queue manager 'QM1' complete. [ArithInsert1(4), CommentInsert1(QM1), CommentInsert2(<0:0:0:4820> 0x00000000000012d4)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.004Z AMQ7231I: 0 log records accessed on queue manager 'QM1' during the recovery phase. [CommentInsert1(QM1)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.542Z Starting queue manager
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.004Z AMQ7232I: Transaction manager state recovered for queue manager 'QM1'. [CommentInsert1(QM1)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.005Z AMQ7233I: 0 out of 0 in-flight transactions resolved for queue manager 'QM1'. [CommentInsert1(QM1)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.267Z AMQ8048I: Default objects statistics : 85 created. 0 replaced. 0 failed. [ArithInsert1(85), CommentInsert1(0)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.270Z AMQ8003I: IBM MQ queue manager 'QM1' started using V9.4.4.0. [CommentInsert1(9.4.4.0), CommentInsert3(QM1)]
2025-10-27 14:19:42.542 | 2025-10-27T19:19:42.412Z AMQ8004I: IBM MQ queue manager 'QM1' ended. [CommentInsert3(QM1)]
2025-10-27 14:19:42.743 | 2025-10-27T19:19:42.743Z Started queue manager
2025-10-27 14:19:42.743 | 2025-10-27T19:19:42.743Z Metrics are disabled
2025-10-27 14:19:42.755 | 2025-10-27T19:19:42.315Z product = WebSphere Application Server 25.0.0.9 (wlp-1.0.105.cl250920250821-1629)\nwlp.install.dir = /opt/mqm/web/\nserver.config.dir = /mnt/mqm/data/web/installations/Installation1/servers/mqweb/\njava.home = /opt/mqm/java/jre64/jre\njava.version = 21.0.8\njava.runtime = IBM Semeru Runtime Open Edition (21.0.8+9-ifix-IJ55402-2025073001)\nos = Linux (6.10.14-linuxkit; aarch64) (en_US)\nprocess = 1549@c3e7b01f928e\nClasspath = /opt/mqm/web/bin/tools/ws-server.jar\nJava Library path = /opt/mqm/java/jre64/jre/lib/default:/opt/mqm/java/jre64/jre/lib:/opt/mqm/java/lib64:/opt/mqm/lib64:/opt/mqm/lib:/usr/lib64:/usr/lib\n
2025-10-27 14:19:42.755 | 2025-10-27T19:19:42.336Z CWWKE0001I: The server mqweb has been launched.
2025-10-27 14:19:43.041 | 2025-10-27T19:19:42.545Z AMQ6206I: Command strmqm was issued. [CommentInsert1(strmqm), CommentInsert2(strmqm -x QM1)]
2025-10-27 14:19:43.043 | 2025-10-27T19:19:42.584Z Initializing MQ Advanced for Developers custom authentication service
2025-10-27 14:19:43.043 | 2025-10-27T19:19:42.545Z AMQ5782I: File system containing '/mnt/mqm/data/qmgrs/QM1' is 14.49% used, 868512MB free. [ArithInsert1(14.49), CommentInsert1(/mnt/mqm/data/qmgrs/QM1), CommentInsert2(1015667), CommentInsert3(868512)]
2025-10-27 14:19:43.043 | 2025-10-27T19:19:42.546Z AMQ5782I: File system containing '/mnt/mqm/data/log/QM1' is 14.49% used, 868528MB free. [ArithInsert1(14.49), CommentInsert1(/mnt/mqm/data/log/QM1), CommentInsert2(1015667), CommentInsert3(868528)]
2025-10-27 14:19:43.043 | 2025-10-27T19:19:42.576Z AMQ5775I: Successfully applied automatic configuration INI definitions. [CommentInsert1(INI)]
2025-10-27 14:19:43.043 | 2025-10-27T19:19:42.625Z AMQ7374I: Log replay for the queue manager has started at Log Sequence Number (LSN) '<0:0:8:65136> 0x000000000008fe70'. [ArithInsert1(2), ArithInsert2(589424), CommentInsert1(<0:0:8:65136> 0x000000000008fe70), CommentInsert2(2025-10-27T19:19:42.408198), CommentInsert3(S0000000.LOG)]
2025-10-27 14:19:43.043 | 2025-10-27T19:19:42.625Z AMQ7229I: 6 log records accessed on queue manager 'QM1' during the log replay phase. [ArithInsert1(6), CommentInsert1(QM1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.625Z AMQ7230I: Log replay for queue manager 'QM1' complete. [ArithInsert1(6), CommentInsert1(QM1), CommentInsert2(<0:0:8:65136> 0x000000000008fe70)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.626Z AMQ7231I: 0 log records accessed on queue manager 'QM1' during the recovery phase. [CommentInsert1(QM1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.626Z AMQ7232I: Transaction manager state recovered for queue manager 'QM1'. [CommentInsert1(QM1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.628Z AMQ7233I: 0 out of 0 in-flight transactions resolved for queue manager 'QM1'. [CommentInsert1(QM1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.652Z AMQ9410I: Repository manager started.
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.659Z AMQ8003I: IBM MQ queue manager 'QM1' started using V9.4.4.0. [CommentInsert1(9.4.4.0), CommentInsert3(QM1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.660Z AMQ5024I: The command server has started. ProcessId(1703). [ArithInsert1(1703), CommentInsert1(SYSTEM.CMDSERVER.1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.660Z AMQ5022I: The channel initiator has started. ProcessId(1704). [ArithInsert1(1704), CommentInsert1(SYSTEM.CHANNEL.INITQ)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.668Z AMQ8942I: Starting to process automatic MQSC configuration script.
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.668Z AMQ8024I: IBM MQ channel initiator started. [CommentInsert1(SYSTEM.CHANNEL.INITQ)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.739Z AMQ8939I: Automatic MQSC configuration script has completed, and contained 25 command(s), of which 0 had errors. [ArithInsert1(25), CommentInsert1(0)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.742Z AMQ9722W: Plain text communication is enabled.
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.747Z AMQ5026I: The listener 'SYSTEM.LISTENER.TCP.1' has started. ProcessId(1752). [ArithInsert1(1752), CommentInsert1(SYSTEM.LISTENER.TCP.1)]
2025-10-27 14:19:43.044 | 2025-10-27T19:19:42.753Z AMQ5806I: Queued Publish/Subscribe Daemon started for queue manager QM1. [CommentInsert1(QM1)]
2025-10-27 14:19:43.256 | 2025-10-27T19:19:42.859Z CWWKG0028A: Processing included configuration resource: /opt/mqm/web/mq/etc/mqweb.xml
2025-10-27 14:19:43.256 | 2025-10-27T19:19:42.870Z CWWKG0028A: Processing included configuration resource: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/mqwebcontainer.xml
2025-10-27 14:19:43.256 | 2025-10-27T19:19:42.872Z CWWKG0028A: Processing included configuration resource: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/tls.xml
2025-10-27 14:19:43.256 | 2025-10-27T19:19:42.873Z CWWKG0028A: Processing included configuration resource: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/mqwebexternal.xml
2025-10-27 14:19:43.256 | 2025-10-27T19:19:42.874Z CWWKG0028A: Processing included configuration resource: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/mqwebuser.xml
2025-10-27 14:19:43.256 | 2025-10-27T19:19:43.004Z CWWKE0002I: The kernel started after 0.878 seconds
2025-10-27 14:19:43.256 | 2025-10-27T19:19:43.049Z CWWKF0007I: Feature update started.
2025-10-27 14:19:43.758 | 2025-10-27T19:19:43.605Z CWWKG0102I: Found conflicting settings for httpDispatcher configuration.\n  Property appOrContextRootMissingMessage has conflicting values:\n    Value <script>document.title="";document.getElementsByTagName("h1")[0].innerHTML="";document.location.href=document.location.href.indexOf("ibmmq/console") >= 0?"/ibmmq/console/login.html":"ibmmq/console/login.html";</script> is set in file:/opt/mqm/web/mq/etc/mqweb.xml.\n    Value <script>document.location.href="/ibmmq/console/";</script> is set in file:/mnt/mqm/data/web/installations/Installation1/servers/mqweb/mqwebcontainer.xml.\n  Property appOrContextRootMissingMessage will be set to <script>document.location.href="/ibmmq/console/";</script>.\n
2025-10-27 14:19:44.260 | 2025-10-27T19:19:43.799Z CWWKS0007I: The security service is starting...
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.065Z DYNA1001I: WebSphere Dynamic Cache instance named baseCache initialized successfully.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.066Z DYNA1071I: The cache provider default is being used.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.067Z DYNA1056I: Dynamic Cache (object cache) initialized successfully.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.110Z CWPKI0802I: Creating the SSL certificate. This may take a few seconds.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.113Z CWWKS4103I: Creating the LTPA keys. This may take a few seconds.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.136Z CWWKS1123I: The collective authentication plugin with class name NullCollectiveAuthenticationPlugin has been activated.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.253Z CWWKZ0018I: Starting application com.ibm.mq.rest.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.253Z CWWKZ0018I: Starting application com.ibm.mq.console.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.254Z CWWKZ0136I: The com.ibm.mq.rest application is using the archive file at the /opt/mqm/web/mq/apps/com.ibm.mq.rest.ear location.
2025-10-27 14:19:44.260 | 2025-10-27T19:19:44.254Z CWWKZ0136I: The com.ibm.mq.console application is using the archive file at the /opt/mqm/web/mq/apps/com.ibm.mq.webconsole.ear location.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.304Z CWWKS4104A: LTPA keys created in 0.191 seconds. LTPA key file: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/resources/security/ltpa.keys
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.306Z CWWKS0008I: The security service is ready.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.307Z CWWKS4105I: LTPA configuration is ready after 0.194 seconds.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.658Z SESN8501I: The session manager did not find a persistent storage location; HttpSession objects will be stored in the local application server's memory.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.662Z SRVE0169I: Loading Web Module: mqconsole.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.663Z SRVE0250I: Web Module mqconsole has been bound to default_host.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.709Z SRVE0169I: Loading Web Module: com.ibm.mq.rest.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.709Z SRVE0250I: Web Module com.ibm.mq.rest has been bound to default_host.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.710Z SRVE0169I: Loading Web Module: com.ibm.mq.webconsoleinternal.
2025-10-27 14:19:44.763 | 2025-10-27T19:19:44.710Z SRVE0250I: Web Module com.ibm.mq.webconsoleinternal has been bound to default_host.
2025-10-27 14:19:44.928 | 2025-10-27T19:19:44.928Z Started web server
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.780Z SESN0176I: A new session context will be created for application key default_host/ibmmq/console
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.787Z SESN0172I: The session manager is using the Java default SecureRandom implementation for session ID generation.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.789Z SESN0176I: A new session context will be created for application key default_host/ibmmq/console/internal
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.791Z SESN0176I: A new session context will be created for application key default_host/ibmmq/rest
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.793Z SESN0172I: The session manager is using the Java default SecureRandom implementation for session ID generation.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.793Z SESN0172I: The session manager is using the Java default SecureRandom implementation for session ID generation.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.796Z DYNA1056I: Dynamic Cache (object cache) initialized successfully.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.830Z SRVE9103I: A configuration file for a web server plugin was automatically generated for this server at /mnt/mqm/data/web/installations/Installation1/servers/mqweb/logs/state/plugin-cfg.xml.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.868Z MQWB2019I: MQ Console level: p944-L251003
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.868Z CWWKS9122I:  For URL /* in application com.ibm.mq.console, the following HTTP methods are uncovered, and accessible: POST PUT DELETE HEAD OPTIONS TRACE
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.887Z MQWB0400E: The MFT REST API is not enabled.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.888Z MQWB0023I: MQ REST API level: p944-L251003
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.890Z MQWB0318I: The mqweb server is configured for 'LOCAL' queue manager REST messaging.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.890Z CWWKZ0001I: Application com.ibm.mq.rest started in 0.637 seconds.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.900Z CWWKZ0001I: Application com.ibm.mq.console started in 0.647 seconds.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.920Z CWWKF0012I: The server installed the following features: [appSecurity-2.0, applicationMonitorMQ-1.0, basicAuthenticationMQ-1.0, concurrent-1.0, distributedMap-1.0, jaxrs-2.1, jaxrsClient-2.1, jndi-1.0, jsonp-1.1, servlet-4.0, ssl-1.0, websocket-1.0].
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.921Z CWWKF0008I: Feature update completed in 1.917 seconds.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:44.921Z CWWKF0011I: The mqweb server is ready to run a smarter planet. The mqweb server started in 2.795 seconds.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:45.091Z CWPKI0803A: SSL certificate created in 0.981 seconds. SSL key file: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/resources/security/key.jks
2025-10-27 14:19:45.264 | 2025-10-27T19:19:45.092Z Successfully loaded default keystore: /mnt/mqm/data/web/installations/Installation1/servers/mqweb/resources/security/key.jks of type: JKS
2025-10-27 14:19:45.264 | 2025-10-27T19:19:45.152Z CWWKO0219I: TCP Channel defaultHttpEndpoint-ssl has been started and is now listening for requests on host *  (IPv6) port 9443.
2025-10-27 14:19:45.264 | 2025-10-27T19:19:45.154Z CWWKT0016I: Web application available (default_host): https://c3e7b01f928e:9443/ibmmq/console/
2025-10-27 14:19:45.264 | 2025-10-27T19:19:45.156Z CWWKT0016I: Web application available (default_host): https://c3e7b01f928e:9443/ibmmq/rest/
2025-10-27 14:19:45.264 | 2025-10-27T19:19:45.158Z CWWKT0016I: Web application available (default_host): https://c3e7b01f928e:9443/ibmmq/console/internal/
2025-10-27 14:20:00.650 | 2025-10-27T19:20:00.214Z Environment variable MQ_ADMIN_PASSWORD is deprecated, use secrets to set the passwords
2025-10-27 14:20:00.650 | 2025-10-27T19:20:00.229Z Environment variable MQ_ADMIN_PASSWORD is deprecated, use secrets to set the passwords