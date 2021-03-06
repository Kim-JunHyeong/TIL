package examples.boot.malljpa.paging;

import lombok.Getter;
import lombok.Setter;

public class PageMaker {
    @Getter
    private int totalItemCount;          // 게시판 전체 데이터 수
    @Setter
    @Getter
    private int displayPageNum = 2; // 게시판 버튼 번호의 개수
    @Setter @Getter
    private int startPage;          // 현재 화면에서 보이는 startPage 번호
    @Setter @Getter
    private int endPage;            // 현재 화면에서 보이는 endPage 번호
    @Setter @Getter
    private boolean prev;           // 이전 버튼 활성화 여부
    @Setter @Getter
    private boolean next;           // 다음 버튼 활성화 여부
    @Setter @Getter
    private Criteria criteria;

    public void setTotalItemCount(int totalItemCount){
        this.totalItemCount = totalItemCount;
        calcData();     // 전체 필드 변수 세팅 : 전체 게시글 수를 얻을 때 필드가 세팅되도록 한다.
    }

    private void calcData(){
        // Math.ceil : 소수점 이하를 올림    현재 페이지 / 게시판 버튼 번호의 개수 ex) 17/10 = 1.7 -> 2 * 10 = endPage = 20
        endPage = (int)(Math.ceil(criteria.getPage()/(double)displayPageNum) * displayPageNum);
        startPage = (endPage - displayPageNum) + 1;     // endPage에서 게시판 버튼 번호의 개수를 빼주면 됨

        // 페이징 버튼의 마지막 수 = 총 게시글의 수 / 보여줄 게시글의 수
        int tempEndPage = (int)(Math.ceil(totalItemCount / (double)criteria.getPerPageNum()));

        if(endPage > tempEndPage){  // 마지막 페이지는 항상 페이징 버튼의 마지막 수보다 커야 함. 작다면 케이징 버튼과 값이 일치해야 한다.
            endPage = tempEndPage;
        }

        // startPage 번호가 1이면 이전 페이지는 0, 따라서 이전 페이지로 이동할 수 없음(displayPageNum 단위로 움직임)
        prev = startPage == 1 ? false : true;
        // next = endPage * criteria.getPerPageNum() >= totalBoardCount ? false : true;
        next = endPage >= tempEndPage ? false : true;
    }
}