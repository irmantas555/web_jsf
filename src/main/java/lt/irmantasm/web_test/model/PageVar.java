package lt.irmantasm.web_test.model;

import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class PageVar {
    private Integer pagesCount;
    private Integer currentPage = 1;
    private Integer pageSize;
    private List<Marking> pagesList = new ArrayList<>();

    public static PageVar fromList(Integer listSize, Integer pageSize) {
        if (listSize != null) {
            Integer pagesCount = listSize / pageSize + 1;
            List<Marking> markings = getMarkings(pagesCount);
            return new PageVar(listSize / pageSize + 1, 1, pageSize, markings);
        }
        return null;
    }

    private static List<Marking> getMarkings(Integer pagesCount) {
        List<Integer> collect = IntStream.range(0, pagesCount + 1).boxed().collect(Collectors.toList());
        collect.add(-1);
        return collect.stream().map(i -> new Marking(i, toMark(i))).collect(Collectors.toList());
    }

    private static String toMark(Integer integer) {
        String s;
        switch (integer) {
            case 0:
                s = "<<";
                break;
            case -1: s = ">>";
                break;
            default:
                s = integer.toString();
        }
        return s;
    }

    public static void recalculate(PageVar newpage) {
            Integer pagesCount = newpage.pageSize / newpage.pagesCount + 1;
            newpage.pagesCount = pagesCount;
            newpage.pagesList = getMarkings(pagesCount);;
    }

}
