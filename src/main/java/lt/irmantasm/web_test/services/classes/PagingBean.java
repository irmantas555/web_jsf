package lt.irmantasm.web_test.services.classes;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lt.irmantasm.web_test.model.Marking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SessionScoped
@Named
public class PagingBean implements Serializable {
    private final List<Integer> pageSizes = Arrays.asList(5, 10, 20, 50);
    private Integer pageSize = pageSizes.get(0);
    private Integer pagesCount;
    private Integer size;
    private Integer newSize;
    private List<Marking> pagesList = new ArrayList<>();
    private Integer currentPage = 1;
    private Integer newPage = 1;

    public boolean initPage(Integer listSize) {
       return fromList(listSize, pageSize);
    }

    public void setNewPage(Integer newPage) {
        if (newPage.equals(0)) {
            if (currentPage > 1) currentPage = currentPage - 1;
            this.newPage = currentPage;
            return;
        } else if (newPage.equals(-1)) {
            if (currentPage < pagesCount) currentPage = currentPage + 1;
            this.newPage = currentPage;
            return;
        } else if (newPage >= 1 && newPage <= pagesCount) {
            currentPage = newPage;
        }
        this.newPage = newPage;
    }
    public boolean fromList(Integer listSize, Integer ps) {
        if (listSize != null) {
            size = listSize;
            currentPage = 1;
            pagesCount = listSize / ps + 1;
            pagesList = getMarkings(pagesCount);
            pageSize = ps;
            newSize = ps;
        }
        return true;
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
            case -1:
                s = ">>";
                break;
            default:
                s = integer.toString();
        }
        return s;
    }

    public void recalculate(Integer newSize) {
        Integer pc =  size / newSize + 1;
        currentPage = 1;
        pagesCount = pc;
        pageSize = newSize;
        pagesList = getMarkings(pagesCount);
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }

    public void setNewSize(Integer newSize) {
        recalculate(newSize);
        this.newSize = newSize;
    }

    public void setPagesList(List<Marking> pagesList) {
        this.pagesList = pagesList;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Integer> getPageSizes() {
        return pageSizes;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public Integer getNewSize() {
        return newSize;
    }

    public List<Marking> getPagesList() {
        return pagesList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getNewPage() {
        return newPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
