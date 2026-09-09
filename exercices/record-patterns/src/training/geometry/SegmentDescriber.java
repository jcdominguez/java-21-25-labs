package training.geometry;

/// Qualifie un segment à partir de ses deux extrémités.
///
/// Écrit sans record pattern : on teste le type, puis on redescend
/// composant par composant avec les accesseurs.
final class SegmentDescriber {

    String describe(Object value) {
        if (value instanceof Segment segment) {
            if (segment.start().y() == segment.end().y()) {
                return "horizontal";
            }
            if (segment.start().x() == segment.end().x()) {
                return "vertical";
            }
            return "segment";
        }
        return "pas un segment";
    }
}
