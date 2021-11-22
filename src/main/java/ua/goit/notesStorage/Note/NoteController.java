package ua.goit.notesStorage.Note;

import ua.goit.notesStorage.authorization.User;
import ua.goit.notesStorage.authorization.UserService;
import ua.goit.notesStorage.enums.AccessTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Validated
@Controller
@RequestMapping(value = "/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public String getNotes(@AuthenticationPrincipal User user,@RequestParam(required = false,defaultValue = "") String filter, Map<String, Object> model){
        List<Note> notes;
        if (filter != null || !filter.isEmpty()) {
            user = userService.getById(user.getId());
            notes = noteService.getListNotes(user.getId());
        } else {
            notes = noteService.getListNotes(user.getId());
        }
        int noteCount= notes.size();
        model.put("notes", notes);
        model.put("filter", filter);
        model.put("noteCount", noteCount);
        //model.put("message", "TEST MESSAGE!"); //for view testing
        return "noteList";
    }

    @GetMapping("create")
    public String noteCreate(Map<String, Object> model){
        return "noteCreate";
    }

    @GetMapping("edit/{id}")
    public String noteEdit(@PathVariable String id,  Map<String, Object> model){
        Note note = noteService.getById(UUID.fromString(id));
        if (note != null){
            model.put("editNote", note);
        }
        return "noteEdit";
    }

    @GetMapping("delete/{id}")
    public String noteDelete(@PathVariable String id, Map<String, Object> model){
        noteService.deleteById(UUID.fromString(id));
        return "redirect:/note/list";
    }

    @GetMapping("error")
    public String noteError(Map<String, Object> model){
        model.put("message", "TEST MESSAGE!"); //for view testing
        return "noteError";
    }

    @GetMapping("share/{id}")
    public String noteShow(@AuthenticationPrincipal User user,@PathVariable String id, Map<String,Object> model){
        Optional<Note> note = noteService.findById(UUID.fromString(id));
        if ((!note.isEmpty() && ((user!=null && note.get().getAuthor().getId().equals(user.getId())) ||
                (user == null && note.get().getAccessType().equals(AccessTypes.PUBLIC))))){
        model.put("note", note.get());
        } else {
            model.put("message", "We can't find tis note ");
        }
        return "noteShow";
    }

    @PostMapping("create")
    public String addNote(@AuthenticationPrincipal User user,
                          @ModelAttribute("editNote") Note editNote,
                          @RequestParam(required = false) String noteId,
                          @RequestParam(required = false) String accessType,
                          Map<String, Object> model){
        if (!noteId.isBlank()) {
            editNote.setId(UUID.fromString(noteId));
            editNote.setAccessType(AccessTypes.valueOf(accessType.toUpperCase()));
        }
        editNote.setAuthor(user);
        noteService.save(editNote);
        return "redirect:/note/list";
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ModelAndView onConstraintValidationException(ConstraintViolationException e, Model model) {
        List<String> error = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations){
            error.add(violation.getMessage());
        }
        model.addAttribute("message",error);
        return new ModelAndView("noteError");
    }
}
