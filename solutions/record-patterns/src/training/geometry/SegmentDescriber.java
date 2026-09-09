package training.geometry;

/// Qualifie un segment à partir de ses deux extrémités.
///
/// Les record patterns imbriqués lient directement les quatre coordonnées :
/// plus aucun accesseur n'apparaît dans les conditions.
final class SegmentDescriber {

    String describe(Object value) {
        return switch (value) {
            case Segment(Point(int x1, int y1), Point(int x2, int y2))
                    when x1 == x2 && y1 == y2 -> "point unique";
            case Segment(Point(int x1, int y1), Point(int x2, int y2))
                    when y1 == y2 -> "horizontal";
            case Segment(Point(int x1, int y1), Point(int x2, int y2))
                    when x1 == x2 -> "vertical";
            case Segment segment -> "oblique";
            default -> "pas un segment";
        };
    }
}
