package support.base.pojo.po;

public class SweetGoingto {
    private String sgId;

    private String name;

    private String goingTo;

    public String getSgId() {
        return sgId;
    }

    public void setSgId(String sgId) {
        this.sgId = sgId == null ? null : sgId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGoingTo() {
        return goingTo;
    }

    public void setGoingTo(String goingTo) {
        this.goingTo = goingTo == null ? null : goingTo.trim();
    }
}