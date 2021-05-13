
package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Captcha extends Component{

    private Component head;
    private Component body;
    
    /* Utilizacion */
    private Integer times;
    private Integer success;
    private Integer mistakes;
    private String date;

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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getMistakes() {
        return mistakes;
    }

    public void setMistakes(Integer mistakes) {
        this.mistakes = mistakes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
