package nl.sijpesteijn.lasforce.rest;

import com.google.gson.*;
import nl.sijpesteijn.lasforce.domain.LaserAnimation;
import nl.sijpesteijn.lasforce.domain.Layer;
import nl.sijpesteijn.lasforce.domain.shapes.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: Gijs Sijpesteijn
 */
public class LaserAnimationSerializer implements JsonSerializer<LaserAnimation> {

    @Override
    public JsonElement serialize(final LaserAnimation animation, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        JsonObject element = new JsonObject();
        element.addProperty("name", animation.getName());
        element.add("layers", createLayers(animation.getLayers()));
        return element;
    }

    private JsonElement createLayers(final List<Layer> layers) {
        JsonArray jsonArray = new JsonArray();
        for(Layer layer : layers) {
            JsonArray layerArray = new JsonArray();
            layerArray.add(new JsonPrimitive("Layer"));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", layer.getName());
            jsonObject.addProperty("visible", layer.isVisible());
            jsonObject.addProperty("applyMatrix", layer.isApplyMatrix());
            jsonObject.add("children", createChildren(layer.getChildren()));
            layerArray.add(jsonObject);
            jsonArray.add(layerArray);
        }
        return jsonArray;
    }

    private JsonElement createChildren(final List<Child> children) {
        JsonArray jsonArray = new JsonArray();
        for(Child child : children) {
            JsonArray childArray = new JsonArray();
            JsonObject childObject = new JsonObject();
            if (child instanceof Path) {
                childArray.add(new JsonPrimitive("Path"));
                Path path = (Path) child;
                childObject.addProperty("applyMatrix", path.isApplyMatrix());
                childObject.addProperty("closed", path.isClosed());
                childObject.addProperty("strokeWidth", path.getStrokeWidth());
                childObject.add("strokeColor", createStrokeColor(path.getStrokeColor()));
                childObject.add("segments", createSegments(path.getSegments()));
            }
            if (child instanceof Group) {
                childArray.add(new JsonPrimitive("Group"));
                Group group = (Group) child;
                childObject.addProperty("applyMatrix", group.isApplyMatrix());
                childObject.add("children", createChildren(group.getChildren()));
            }
            if (child instanceof PointText) {
                childArray.add(new JsonPrimitive("PointText"));
                PointText pointText = (PointText) child;
                childObject.addProperty("applyMatrix", pointText.isApplyMatrix());
                childObject.add("matrix", createMatrix(pointText.getMatrix()));
                childObject.addProperty("content", pointText.getContent());
                childObject.add("fillColor", createFillColor(pointText.getFillColor()));
                childObject.addProperty("fontFamily", pointText.getFontFamily());
                childObject.addProperty("fontWeight", pointText.getFontWeight());
                childObject.addProperty("fontSize", pointText.getFontSize());
                childObject.addProperty("font", pointText.getFont());
                childObject.addProperty("leading", pointText.getLeading());
            }
            childArray.add(childObject);
            jsonArray.add(childArray);
        }
        return jsonArray;
    }

    private JsonElement createSegments(final List<List<Segment>> segments) {
        JsonArray jsonArray = new JsonArray();
        for(List<Segment> subSegments : segments) {
            if (subSegments.size() > 1) {
                JsonArray subArray = new JsonArray();
                jsonArray.add(subArray);
                for (Segment segment : subSegments) {
                    JsonArray segmentArray = new JsonArray();
                    segmentArray.add(new JsonPrimitive(segment.getX()));
                    segmentArray.add(new JsonPrimitive(segment.getY()));
                    subArray.add(segmentArray);
                }
            } else {
                for (Segment segment : subSegments) {
                    JsonArray segmentArray = new JsonArray();
                    segmentArray.add(new JsonPrimitive(segment.getX()));
                    segmentArray.add(new JsonPrimitive(segment.getY()));
                    jsonArray.add(segmentArray);
                }
            }
        }
        return jsonArray;
    }

    private JsonElement createFillColor(final FillColor fillColor) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(fillColor.getBlue()));
        jsonArray.add(new JsonPrimitive(fillColor.getGreen()));
        jsonArray.add(new JsonPrimitive(fillColor.getRed()));
        return jsonArray;

    }

    private JsonElement createMatrix(final Matrix matrix) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(matrix.getA()));
        jsonArray.add(new JsonPrimitive(matrix.getB()));
        jsonArray.add(new JsonPrimitive(matrix.getC()));
        jsonArray.add(new JsonPrimitive(matrix.getD()));
        jsonArray.add(new JsonPrimitive(matrix.getXt()));
        jsonArray.add(new JsonPrimitive(matrix.getYt()));
        return jsonArray;
    }

    private JsonElement createStrokeColor(final StrokeColor strokeColor) {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive(strokeColor.getBlue()));
        jsonArray.add(new JsonPrimitive(strokeColor.getGreen()));
        jsonArray.add(new JsonPrimitive(strokeColor.getRed()));
        return jsonArray;
    }

}
