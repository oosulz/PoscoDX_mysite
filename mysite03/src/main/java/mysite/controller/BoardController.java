package mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.repository.BoardRepository;
import mysite.service.BoardService;
import mysite.vo.BoardVo;



@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping
    public String BoardList(@RequestParam(name = "pageNum", defaultValue = "1") int boardPage, Model model) {
        Map<String, Object> result = boardService.getContentList(boardPage);
        model.addAllAttributes(result);

        return "board/list";
    }
	
	@RequestMapping(value = "/detail", method=RequestMethod.GET)
    public String BoardDetail(@RequestParam("id") Long id, Model model) {
        BoardVo result = boardService.getContents(id);
        model.addAttribute("boardContent",result);
        return "board/view";
    }
	
	@RequestMapping(value = "/delete")
    public String BoardDelete(@RequestParam("id") Long id) {
        boardService.deleteContents(id);
        return "redirect:/board?pageNum=1";
    }
	
	@RequestMapping(value = "/write")
    public String BoardWrite(@RequestParam("id") Long id) {
        boardService.deleteContents(id);
        return "redirect:/board?pageNum=1";
    }
	
	
}
