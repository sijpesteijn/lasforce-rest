package nl.sijpesteijn.lasforce.domain;

import com.google.gson.*;
import nl.sijpesteijn.lasforce.domain.shapes.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class MongoToAnimationSerializer implements JsonDeserializer<LaserAnimation> {

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
            JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();
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
            JsonObject childObject = jsonArray.get(i).getAsJsonObject();
            String type = childObject.get("type").getAsString();
            if (type.equals("Path")) {
                Path path = new Path();
                path.setApplyMatrix(childObject.get("applyMatrix").getAsBoolean());
                if (childObject.has("closed")) {
                    path.setClosed(childObject.get("closed").getAsBoolean());
                } else {
                    path.setClosed(false);
                }
                path.setStrokeWidth(childObject.get("strokeWidth").getAsInt());
                path.setStrokeColor(getStrokeColor(childObject.get("strokeColor").getAsJsonObject()));
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
                pointText.setMatrix(getMatrix(childObject.get("matrix").getAsJsonObject()));
                pointText.setContent(childObject.get("content").getAsString());
                pointText.setFillColor(getFillColor(childObject.get("fillColor").getAsJsonObject()));
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

    private FillColor getFillColor(final JsonObject jsonObject) {
        double blue = jsonObject.get("blue").getAsJsonPrimitive().getAsDouble();
        double green = jsonObject.get("green").getAsJsonPrimitive().getAsDouble();
        double red = jsonObject.get("red").getAsJsonPrimitive().getAsDouble();
        return new FillColor(blue, green, red);
    }

    private Matrix getMatrix(final JsonObject jsonObject) {
        double a = jsonObject.get("a").getAsJsonPrimitive().getAsDouble();
        double b = jsonObject.get("b").getAsJsonPrimitive().getAsDouble();
        double c = jsonObject.get("c").getAsJsonPrimitive().getAsDouble();
        double d = jsonObject.get("d").getAsJsonPrimitive().getAsDouble();
        double xt = jsonObject.get("xt").getAsJsonPrimitive().getAsDouble();
        double yt = jsonObject.get("yt").getAsJsonPrimitive().getAsDouble();
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
                    JsonObject subSegment = jsonArray.get(j).getAsJsonObject();
                    Segment segment = getSegment(subSegment);
                    subSegments.add(segment);
                }
            } else {
                Segment segment = getSegment(jsonArray.get(0).getAsJsonObject());
                subSegments.add(segment);
            }
        }
        return segments;
    }

    private Segment getSegment(final JsonObject jsonObject) {
        return new Segment(jsonObject.get("x").getAsJsonPrimitive().getAsDouble(),
                jsonObject.get("y").getAsJsonPrimitive()
                .getAsDouble());
    }

    private StrokeColor getStrokeColor(final JsonObject jsonObject) {
        double blue = jsonObject.get("blue").getAsJsonPrimitive().getAsDouble();
        double green = jsonObject.get("green").getAsJsonPrimitive().getAsDouble();
        double red = jsonObject.get("red").getAsJsonPrimitive().getAsDouble();
        return new StrokeColor(blue, green, red);
    }

}
