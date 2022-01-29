CREATE DATABASE JAVA;

USE JAVA;

CREATE TABLE ACCOUNTS(
	USERNAME	VARCHAR(16)		PRIMARY KEY	,
    HASHPASS	VARCHAR(60)					,
    EMAIL		VARCHAR(60)		UNIQUE
);

CREATE TABLE APPOINTMENTS (
	EID			INT				PRIMARY KEY	,
    EUSERNAME	VARCHAR(16)					,
    ENAME		VARCHAR(60)		NOT NULL	,
    ELOCATION	VARCHAR(60)					,
    EDAY		INT				NOT NULL	,
    EMONTH		INT				NOT NULL	,
    EYEAR		INT				NOT NULL	,
    EHOUR		INT				NOT NULL	,
    EMINUTE		INT				NOT NULL	,
    EPRIORITY	ENUM('1', '2', '3')			,
    FOREIGN KEY (EUSERNAME) REFERENCES ACCOUNTS (USERNAME) ON DELETE CASCADE
);

CREATE TABLE PARTICIPANTS (
	PEMAIL		VARCHAR(60)					,
    PID			INT 						,
    PRIMARY KEY (PEMAIL, PID)				,
    FOREIGN KEY (PEMAIL) REFERENCES ACCOUNTS (EMAIL) ON DELETE CASCADE	,
    FOREIGN KEY (PID) REFERENCES APPOINTMENTS (EID)	ON DELETE CASCADE
);

