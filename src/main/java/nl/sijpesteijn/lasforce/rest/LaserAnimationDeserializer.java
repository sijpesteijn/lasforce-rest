package nl.sijpesteijn.lasforce.rest;

import com.google.gson.*;
import nl.sijpesteijn.lasforce.domain.LaserAnimation;
import nl.sijpesteijn.lasforce.domain.Layer;
import nl.sijpesteijn.lasforce.domain.shapes.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class LaserAnimationDeserializer implements JsonDeserializer<LaserAnimation> {

    @Override
    public LaserAnimation deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        LaserAnimation animation = new LaserAnimation();
        animation.setName(jsonObject.get("name").getAsString());
        animation.setLayers(getLayers(jsonObject));
        return animation;
    }

    private List<Layer> getLayers(final JsonObject jsonObject) {
        List<Layer> layers = new ArrayList<Layer>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("layers");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            JsonArray layerArrayElement = jsonElement.getAsJsonArray();
            JsonObject jsonObject1 = layerArrayElement.get(1).getAsJsonObject();
            Layer layer = new Layer();
            layer.setName(jsonObject1.get("name").getAsString());
            if (jsonObject1.has("visible")) {
                layer.setVisible(jsonObject1.get("visible").getAsBoolean());
            } else {
                layer.setVisible(true);
            }
            layer.setApplyMatrix(jsonObject1.get("applyMatrix").getAsBoolean());
            JsonArray children = jsonObject1.getAsJsonArray("children");
            layer.setChildren(getChildren(children));
            layers.add(layer);
        }
        return layers;
    }

    private List<Child> getChildren(final JsonArray jsonArray) {
        List<Child> children = new ArrayList<Child>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            JsonArray childArray = jsonElement.getAsJsonArray();
            String type = childArray.get(0).getAsString();
            JsonObject childObject = childArray.get(1).getAsJsonObject();
            if (type.equals("Path")) {
                Path path = new Path();
                path.setApplyMatrix(childObject.get("applyMatrix").getAsBoolean());
                if (childObject.has("closed")) {
                    path.setClosed(childObject.get("closed").getAsBoolean());
                } else {
                    path.setClosed(false);
                }
                path.setStrokeWidth(childObject.get("strokeWidth").getAsInt());
                path.setStrokeColor(getStrokeColor(childObject.get("strokeColor").getAsJsonArray()));
                path.setSegments(getSegments(childObject.get("segments").getAsJsonArray()));
                children.add(path);
            }
            if (type.equals("Group")) {
                JsonArray subChildren = childObject.get("children").getAsJsonArray();
                Group group = new Group();
                group.setApplyMatrix(childObject.get("applyMatrix").getAsBoolean());
                group.setChildren(getChildren(subChildren));
                children.add(group);
            }
            if (type.equals("PointText")) {
                PointText pointText = new PointText();
                pointText.setApplyMatrix(childObject.get("applyMatrix").getAsBoolean());
                pointText.setMatrix(getMatrix(childObject.get("matrix").getAsJsonArray()));
                pointText.setContent(childObject.get("content").getAsString());
                pointText.setFillColor(getFillColor(childObject.get("fillColor").getAsJsonArray()));
                pointText.setFontFamily(childObject.get("fontFamily").getAsString());
                pointText.setFontWeight(childObject.get("fontWeight").getAsString());
                pointText.setFontSize(childObject.get("fontSize").getAsJsonPrimitive().getAsInt());
                pointText.setFont(childObject.get("font").getAsString());
                pointText.setLeading(childObject.get("leading").getAsJsonPrimitive().getAsInt());
                children.add(pointText);
            }
        }
        return children;
    }

    private FillColor getFillColor(final JsonArray jsonArray) {
        double blue = jsonArray.get(0).getAsJsonPrimitive().getAsDouble();
        double green = jsonArray.get(1).getAsJsonPrimitive().getAsDouble();
        double red = jsonArray.get(2).getAsJsonPrimitive().getAsDouble();
        return new FillColor(blue, green, red);
    }

    private Matrix getMatrix(final JsonArray jsonArray) {
        double a = jsonArray.get(0).getAsJsonPrimitive().getAsDouble();
        double b = jsonArray.get(1).getAsJsonPrimitive().getAsDouble();
        double c = jsonArray.get(2).getAsJsonPrimitive().getAsDouble();
        double d = jsonArray.get(3).getAsJsonPrimitive().getAsDouble();
        double xt = jsonArray.get(4).getAsJsonPrimitive().getAsDouble();
        double yt = jsonArray.get(5).getAsJsonPrimitive().getAsDouble();
        return new Matrix(a, b, c, d, xt, yt);
    }

    private List<List<Segment>> getSegments(final JsonArray segmentArray) {
        List<List<Segment>> segments = new ArrayList<List<Segment>>();
        for (int i = 0; i < segmentArray.size(); i++) {
            List<Segment> subSegments = new ArrayList<Segment>();
            segments.add(subSegments);
            JsonArray jsonArray = segmentArray.get(i).getAsJsonArray();
            if (jsonArray.size() > 2) {
                for (int j = 0; j < jsonArray.size(); j++) {
                    JsonArray subSegment = jsonArray.get(j).getAsJsonArray();
                    Segment segment = getSegment(subSegment);
                    subSegments.add(segment);
                }
            } else {
                Segment segment = getSegment(jsonArray);
                subSegments.add(segment);
            }
        }
        return segments;
    }

    private Segment getSegment(final JsonArray jsonArray) {
        return new Segment(jsonArray.get(0).getAsJsonPrimitive().getAsDouble(), jsonArray.get(1).getAsJsonPrimitive()
                .getAsDouble());
    }

    private StrokeColor getStrokeColor(final JsonArray jsonArray) {
        double blue = jsonArray.get(0).getAsDouble();
        double green = jsonArray.get(1).getAsDouble();
        double red = jsonArray.get(2).getAsDouble();
        return new StrokeColor(blue, green, red);
    }

}
