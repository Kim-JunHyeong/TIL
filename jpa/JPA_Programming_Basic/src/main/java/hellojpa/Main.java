package hellojpa;

import hellojpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // hello 라는 이름을 가지고 EntityManagerFactory를 띄운다
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction(); // jpa의 모든 활동은 트랜잭션 안에서 이뤄진다.
        tx.begin();// 트랜잭션 시작

        try {
            Member member = new Member();
            member.setId(100L);
            member.setName("안녕하세요");

            em.persist(member); // persist : 영구저장

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 엔티티 매니저는 끝나고 항상 닫아줘야 한다.
        }
        emf.close();
    }
}
/* 주의
but 스프링 프레임워크를 쓸 때는 이런 고민을 별로 할 필요가 없다...(트러블 슈팅 할 때에나 필요할까?)
 */