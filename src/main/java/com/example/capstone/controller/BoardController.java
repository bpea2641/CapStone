package com.example.capstone.controller;

import com.example.capstone.dto.BoardDTO;
import com.example.capstone.dto.CommentDTO;
import com.example.capstone.entity.BoardEntity;
import com.example.capstone.repository.BoardRepository;
import com.example.capstone.service.BoardService;
import com.example.capstone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final BoardRepository boardRepository;
    @GetMapping("/board/write")
    public String write(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "Board/board_write";
    }

    @GetMapping("/board/save")
    public String saveForm() {
        return "Board/board_save";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "Board/board_list";
    }

    @GetMapping("/board/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "Board/board_list";
    }

    @GetMapping("/board/list/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고,
            게시글 데이터를 가져와서 detail.html에 출력
        * */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "Board/board_detail";
    }

    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "Board/board_update";
    }

    @PostMapping("/board/update/{id}")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "redirect:/board/list/"+boardDTO.getId();
    }

    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }

    // /board/paging?page=1
    @GetMapping("/board/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();

        Page<BoardDTO> boardList = boardService.paging(pageable);

        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Board/board_paging";
    }

    @GetMapping("/board/boardList")
    public String boardList(Model model, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<BoardEntity> boards = boardRepository.findByBoardTitleContainingOrBoardContentsContaining(searchText, searchText, pageable);
//        int blockLimit = 3;
//        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
//        int endPage = ((startPage + blockLimit - 1) < boards.getTotalPages()) ? startPage + blockLimit - 1 : boards.getTotalPages();

        model.addAttribute("boards", boards);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);

        return "Board/boardList";
    }
}
