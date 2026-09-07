package university.backend.forecast;

public record GpaForecast(double currentGpa, double projectedGpa, String direction, double delta) {
    public boolean improving() { return projectedGpa > currentGpa; }
    public boolean declining() { return projectedGpa < currentGpa; }
}
