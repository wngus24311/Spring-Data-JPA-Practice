package org.zerock.ex2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        // DB에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("============testSelect============");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println("memo = " + memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100L;

        Memo memo = memoRepository.getReferenceById(mno);

        System.out.println("============testSelect2============");

        System.out.println("memo = " + memo);
    }

    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
        Optional<Memo> result = memoRepository.findById(100L);

        if (result.isPresent()) {
            System.out.println("memo = " + memo);
        }
    }

    @Test
    public void testDelete() {
        memoRepository.deleteById(99L);
    }

    @Test
    public void testPageDefault() {
        // 1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("result = " + result);
        System.out.println("----------------------------");
        System.out.println("result.getTotalPages() = " + result.getTotalPages());   // 총 페이지
        System.out.println("result.getTotalElements() = " + result.getTotalElements()); // 전체 갯수
        System.out.println("result.getNumber() = " + result.getNumber());   // 현재 페이지 번호 0부터 시작
        System.out.println("result.getSize() = " + result.getSize());   // 페이지당 데이터 개수
        System.out.println("result.hasNext() = " + result.hasNext());   // 다음 페이지 존재 여부
        System.out.println("result.isFirst() = " + result.isFirst());   // 시작 페이지(0) 여부

        System.out.println("========================");
        for (Memo memo:result.getContent()) {
            System.out.println("memo = " + memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort = Sort.by("mno").ascending();
        Sort sort1 = Sort.by("mno").descending();
        Sort sortAll = sort.and(sort1);

        Pageable pageable = PageRequest.of(0, 10, sort);
        Pageable pageable1 = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);
        Page<Memo> result1 = memoRepository.findAll(pageable1);

        result.get().forEach(memo -> {
            System.out.println("memo = " + memo);
        });

        result1.get().forEach(memo -> {
            System.out.println("memo1 = " + memo);
        });
    }

}