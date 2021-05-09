
package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Captcha extends Component{

    private Component head;
    private Component body;

    public Captcha() {
        super();
    }

    public Component getHead() {
        return head;
    }

    public void setHead(Component head) {
        this.head = head;
    }

    public Component getBody() {
        return body;
    }

    public void setBody(Component body) {
        this.body = body;
    }
}
