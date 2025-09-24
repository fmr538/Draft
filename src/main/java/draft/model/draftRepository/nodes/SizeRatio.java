package draft.model.draftRepository.nodes;

public class SizeRatio {
    private double widthRatio;
    private double heightRatio;

    public SizeRatio(double width, double height) {
        widthRatio = width;
        heightRatio = height;
    }


    public double getWidthRatio() {
        return widthRatio;
    }

    public void setWidthRatio(float widthRatio) {
        this.widthRatio = widthRatio;
    }

    public double getHeightRatio() {
        return heightRatio;
    }

    public void setHeightRatio(float heightRatio) {
        this.heightRatio = heightRatio;
    }

    @Override
    public String toString() {
        return "SizeRatio[widthRatio=" + widthRatio + ",heightRatio=" + heightRatio + "]";
    }
}
