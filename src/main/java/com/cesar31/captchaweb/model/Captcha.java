
package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Captcha {

    private ComponentParent head;
    private ComponentParent body;

    public Captcha() {
    }

    public ComponentParent getHead() {
        return head;
    }

    public void setHead(ComponentParent head) {
        this.head = head;
    }

    public ComponentParent getBody() {
        return body;
    }

    public void setBody(ComponentParent body) {
        this.body = body;
    }
}
