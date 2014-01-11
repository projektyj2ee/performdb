package com.performgroup.interview.domain;


/**
 * Possible statuses for a video object in the Perform CMS
 */
public enum VideoStatus {

    /**
     * Live
     */
    LIVE,

    /**
     * Transcoding
     */
    TRANSCODING,
    
    /**
     * Expired
     */
    EXPIRED,
    
    /**
     * Ready to be transcoded
     */
    READY
}
