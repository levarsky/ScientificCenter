DROP TABLE IF EXISTS `ClientDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ClientDetails` (
                                 `appId` varchar(256) NOT NULL,
                                 `resourceIds` varchar(256) DEFAULT NULL,
                                 `appSecret` varchar(256) DEFAULT NULL,
                                 `scope` varchar(256) DEFAULT NULL,
                                 `grantTypes` varchar(256) DEFAULT NULL,
                                 `redirectUrl` varchar(256) DEFAULT NULL,
                                 `authorities` varchar(256) DEFAULT NULL,
                                 `access_token_validity` int(11) DEFAULT NULL,
                                 `refresh_token_validity` int(11) DEFAULT NULL,
                                 `additionalInformation` varchar(4096) DEFAULT NULL,
                                 PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClientDetails`
--





DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
                                      `token_id` varchar(256) DEFAULT NULL,
                                      `token` blob,
                                      `authentication_id` varchar(256) DEFAULT NULL,
                                      `user_name` varchar(256) DEFAULT NULL,
                                      `client_id` varchar(256) DEFAULT NULL,
                                      `authentication` blob,
                                      `refresh_token` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_access_token`
--


DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
                                        `client_id` varchar(256) NOT NULL,
                                        `resource_ids` varchar(256) DEFAULT NULL,
                                        `client_secret` varchar(256) DEFAULT NULL,
                                        `scope` varchar(256) DEFAULT NULL,
                                        `authorized_grant_types` varchar(256) DEFAULT NULL,
                                        `web_server_redirect_uri` varchar(256) DEFAULT NULL,
                                        `authorities` varchar(256) DEFAULT NULL,
                                        `access_token_validity` int(11) DEFAULT NULL,
                                        `refresh_token_validity` int(11) DEFAULT NULL,
                                        `additional_information` varchar(4096) DEFAULT NULL,
                                        PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--


--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_code` (
                              `code` varchar(256) DEFAULT NULL,
                              `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_code`
--


--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_refresh_token` (
                                       `token_id` varchar(256) DEFAULT NULL,
                                       `token` blob,
                                       `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


drop table if exists oauth_client_details;
create table oauth_client_details (
                                      client_id VARCHAR(255) PRIMARY KEY,
                                      resource_ids VARCHAR(255),
                                      client_secret VARCHAR(255),
                                      scope VARCHAR(255),
                                      authorized_grant_types VARCHAR(255),
                                      web_server_redirect_uri VARCHAR(255),
                                      authorities VARCHAR(255),
                                      access_token_validity INTEGER,
                                      refresh_token_validity INTEGER,
                                      additional_information VARCHAR(4096),
                                      autoapprove VARCHAR(255)
);
