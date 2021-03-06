package org.jenkinsci.plugins.pipeline.modeldefinition.ast;

import net.sf.json.JSONArray;
import org.jenkinsci.plugins.pipeline.modeldefinition.validator.ModelValidator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents the collection of {@code Stage}s to be executed in the build. Corresponds to {@code Stages}.
 *
 * @author Andrew Bayer
 */
public class ModelASTStages extends ModelASTElement {
    private List<ModelASTStage> stages = new ArrayList<>();
    private final UUID uuid;

    public ModelASTStages(Object sourceLocation) {
        super(sourceLocation);
        this.uuid = UUID.randomUUID();
    }

    @Override
    @Nonnull
    public Object toJSON() {
        return toJSONArray(stages);
    }

    @Override
    public void validate(@Nonnull final ModelValidator validator) {
        validate(validator, false);
    }

    public void validate(final ModelValidator validator, boolean isWithinParallel) {
        validator.validateElement(this);
        for (ModelASTStage stage : stages) {
            stage.validate(validator, isWithinParallel);
        }
    }

    @Override
    @Nonnull
    public String toGroovy() {
        return toGroovyBlock("stages", stages);
    }

    @Override
    public void removeSourceLocation() {
        super.removeSourceLocation();
        removeSourceLocationsFrom(stages);
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<ModelASTStage> getStages() {
        return stages;
    }

    public void setStages(List<ModelASTStage> stages) {
        this.stages = stages;
    }

    @Override
    public String toString() {
        return "ModelASTStages{" +
                "stages=" + stages +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ModelASTStages that = (ModelASTStages) o;

        return getStages() != null ? getStages().equals(that.getStages()) : that.getStages() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getStages() != null ? getStages().hashCode() : 0);
        return result;
    }
}
