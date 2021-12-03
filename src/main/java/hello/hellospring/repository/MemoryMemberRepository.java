package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

//    Q. 동시성문제 / static 쓰는 이유

//    실무에서는 동시성 문제로 인해 공유되는 변수일 경우에는 HashMap 이 아닌 ConcurrentHashMap 을 써야됨 https://pplenty.tistory.com/17 참조 링크
    private static Map<Long, Member> store = new HashMap<>();
//    실무에서는 동시성 문제로 공유되는 변수는 Atomic long 으로 해야됨 https://bestugi.tistory.com/41 참조 링크
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
//        Q. 람다를 사용한다는데 람다가 무엇인지?
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
