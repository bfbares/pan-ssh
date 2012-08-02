-- ----------------------------------------------------------------------------
-- PAN-SSH
-- ----------------------------------------------------------------------------

-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- ----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE LinkVote;
DROP TABLE Report;
DROP TABLE Links;
DROP TABLE Category;
DROP TABLE User;

-- -------------------------------- User --------------------------------------

CREATE TABLE User ( 
	userId BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(60) DEFAULT NULL,
    login VARCHAR(16) NOT NULL,
    password VARCHAR(128) NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lastlogin TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
    karma DECIMAL(10,2) NOT NULL DEFAULT '10.00',
    level enum('AUTODISABLED','DISABLED','NORMAL','ADMIN','GOD') CHARACTER SET utf8 NOT NULL DEFAULT 'NORMAL',
    lang VARCHAR(2) NOT NULL DEFAULT 'es',
    url VARCHAR(128) DEFAULT NULL,
    email VARCHAR(64) NOT NULL,
    ip VARCHAR(15) NOT NULL,
    version BIGINT,
	UNIQUE (login),
	UNIQUE (email),
	CONSTRAINT UserPK PRIMARY KEY(userId)
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE INDEX UserIndexByUserId ON User (userId);
CREATE INDEX UserIndexByKarma ON User (karma);
CREATE INDEX UserIndexByLevel ON User (level);

-- -------------------------------- Category ----------------------------------

CREATE TABLE Category (
    categoryId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    parent BIGINT NOT NULL DEFAULT 0,
    UNIQUE (name),
    CONSTRAINT CategoryPK PRIMARY KEY(categoryId)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE INDEX CategoryIndexByCategoryId ON Category (categoryId);

-- -------------------------------- Links -------------------------------------

CREATE TABLE Links (
    linkId BIGINT NOT NULL AUTO_INCREMENT,
    linkAuthor BIGINT NOT NULL,
    categoryId BIGINT NOT NULL,
    url VARCHAR(128) NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    submited TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP,
    karma DECIMAL(10,2) NOT NULL DEFAULT '0.0',
    tags TEXT,
    status enum('PUBLISHED', 'DISCARD', 'QUEUED') CHARACTER SET utf8 NOT NULL DEFAULT 'QUEUED',
    version BIGINT, 
    UNIQUE (url),
    CONSTRAINT LinksPK PRIMARY KEY(linkId),
    CONSTRAINT LinkUserIdFK FOREIGN KEY(linkAuthor)
        REFERENCES User (userId),
    CONSTRAINT LinkCategoryIdFK FOREIGN KEY(categoryId)
        REFERENCES Category (categoryId),
    INDEX LinksIndexForAuthorFK (linkAuthor),
    INDEX LinksIndexForCategoryFK (categoryId)
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE INDEX LinksIndexByLinkId ON Links (linkId);
CREATE INDEX LinksIndexByKarma ON Links (karma);
CREATE INDEX LinksIndexByStatus ON Links (status);

-- -------------------------------- Report -----------------------------------

CREATE TABLE Report (
    reportId BIGINT NOT NULL AUTO_INCREMENT,
    linkId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    submited TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reason TEXT NOT NULL,
    status enum('PENDING', 'CHECKED') CHARACTER SET utf8 NOT NULL DEFAULT 'PENDING',
    UNIQUE(linkId),
    CONSTRAINT ReportPK PRIMARY KEY(reportId),
    CONSTRAINT ReportLinkFK FOREIGN KEY(linkId)
        REFERENCES Links (linkId),
    CONSTRAINT ReportUserFK FOREIGN KEY(userId)
        REFERENCES User (userId),
    INDEX ReportIndexForLinkFK(linkId),
    INDEX ReportIndexForUserFK(userId)
    ) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE INDEX ReportIndexByReportId ON Report (reportId);
CREATE INDEX ReportIndexBySubmited ON Report (submited);
CREATE INDEX ReportIndexByStatus ON Report (status);

-- -------------------------------- LinkVote -------------------------------------

CREATE TABLE LinkVote (
    voteId BIGINT NOT NULL AUTO_INCREMENT,
    linkId BIGINT NOT NULL,
    userId BIGINT,
    submited TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    karma DECIMAL(10,2) NOT NULL DEFAULT '4.0',
    type enum('UPVOTE', 'DOWNVOTE') CHARACTER SET utf8 NOT NULL,
    ip VARCHAR(15) NOT NULL,
    CONSTRAINT LinkVotePK PRIMARY KEY(voteId),
    CONSTRAINT LinkVoteLinksFK FOREIGN KEY(linkId)
        REFERENCES Links(linkId),
    CONSTRAINT LinkVoteUserFK FOREIGN KEY(userId)
        REFERENCES User(userId),
    INDEX LinkVoteIndexForLinkFK(linkId),
    INDEX LinkVoteIndexForUserFK(userId)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE INDEX VoteIndexByVoteId ON LinkVote (voteId);
CREATE INDEX VoteIndexByIP ON LinkVote (ip);