package denominator.model;

import static denominator.common.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import denominator.model.profile.Geo;
import denominator.model.profile.Weighted;

/**
 * Capable of building record sets from rdata input types expressed as {@code E}
 * 
 * @param <E>
 *            input type of rdata
 * @param <D>
 *            portable type of the rdata in the {@link ResourceRecordSet}
 */
abstract class AbstractRecordSetBuilder<E, D extends Map<String, Object>, B extends AbstractRecordSetBuilder<E, D, B>> {

    private String name;
    private String type;
    private String qualifier;
    private Integer ttl;
    private List<Map<String, Object>> profile = new ArrayList<Map<String, Object>>();
    private Geo geo;
    private Weighted weighted;

    /**
     * @see ResourceRecordSet#name()
     */
    @SuppressWarnings("unchecked")
    public B name(String name) {
        this.name = name;
        return (B) this;
    }

    /**
     * @see ResourceRecordSet#type()
     */
    @SuppressWarnings("unchecked")
    public B type(String type) {
        this.type = type;
        return (B) this;
    }

    /**
     * @see ResourceRecordSet#qualifier()
     */
    @SuppressWarnings("unchecked")
    public B qualifier(String qualifier) {
        this.qualifier = qualifier;
        return (B) this;
    }

    /**
     * @see ResourceRecordSet#ttl()
     */
    @SuppressWarnings("unchecked")
    public B ttl(Integer ttl) {
        this.ttl = ttl;
        return (B) this;
    }

    /**
     * adds a value to the builder.
     * 
     * ex.
     * 
     * <pre>
     * builder.addProfile(geo);
     * </pre>
     * @deprecated will be removed in version 4.0 for type-safe accessors such
     *             as {@link #geo(Geo)} and {@link #weighted(Weighted)}.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public B addProfile(Map<String, Object> profile) {
        this.profile.add(checkNotNull(profile, "profile"));
        return (B) this;
    }

    /**
     * adds profile values in the builder
     * 
     * ex.
     * 
     * <pre>
     * 
     * builder.addAllProfile(otherProfile);
     * </pre>
     * @deprecated will be removed in version 4.0 for type-safe accessors such
     *             as {@link #geo(Geo)} and {@link #weighted(Weighted)}.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public B addAllProfile(Collection<Map<String, Object>> profile) {
        this.profile.addAll(checkNotNull(profile, "profile"));
        return (B) this;
    }

    /**
     * @see ResourceRecordSet#profiles()
     * @deprecated will be removed in version 4.0 for type-safe accessors such
     *             as {@link #geo(Geo)} and {@link #weighted(Weighted)}.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public B profile(Collection<Map<String, Object>> profile) {
        this.profile = new ArrayList<Map<String, Object>>();
        this.profile.addAll(profile);
        return (B) this;
    }

    /**
     * @see ResourceRecordSet#geo()
     */
    @SuppressWarnings("unchecked")
    public B geo(Geo geo) {
        this.geo = geo;
        return (B) this;
    }

    /**
     * @see ResourceRecordSet#weighted()
     */
    @SuppressWarnings("unchecked")
    public B weighted(Weighted weighted) {
        this.weighted = weighted;
        return (B) this;
    }

    public ResourceRecordSet<D> build() {
        return new ResourceRecordSet<D>(name, type, qualifier, ttl, records(), geo, weighted, profile);
    }

    /**
     * aggregate collected rdata values
     */
    protected abstract List<D> records();
}
