package com.esfe.Asistencia.controladores;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.*;
import com.esfe.Asistencia.Modelos.*;
import com.esfe.Asistencia.Servicios.Interfaces.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/asignaciones") // Define la dirección URL del controller
public class DocenteGrupoController {
    @Autowired
    private IDocenteGrupoService docenteGrupoService;

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private IDocenteService docenteService;

    //Listado
    @GetMapping
    public String index (Model model, 
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        
        int currentPage = page.orElse(1) - 1; // PageRequest usa índice base 0
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        
        Page<DocenteGrupo> asignaciones = docenteGrupoService.buscarTodosPaginados(pageable);
        model.addAttribute("asignaciones", asignaciones);

        int totalPages = asignaciones.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "asignacion/index";                         
}
// CREAR
@GetMapping("/create")
public String create(Model model) {
    model.addAttribute("docenteGrupo", new DocenteGrupo());
    model.addAttribute("docentes", docenteService.obtenerTodos());
    model.addAttribute("grupos", grupoService.obtenerTodos());
    model.addAttribute("action", "create");
    return "asignacion/mant";

}
// EDITAR
@GetMapping("/edit/{id}")
public String edit(@PathVariable Integer id, Model model) {
    DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id);
    model.addAttribute("docenteGrupo", docenteGrupo);
    model.addAttribute("docentes", docenteService.obtenerTodos());
    model.addAttribute("grupos", grupoService.obtenerTodos());
    model.addAttribute("action", "edit");
    return "asignacion/mant";
}

// VISTA
@GetMapping("/view/{id}")
public String view(@PathVariable Integer id, Model model) {
    DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id);
    model.addAttribute("docenteGrupo", docenteGrupo);
    model.addAttribute("docentes", docenteService.obtenerTodos());
    model.addAttribute("grupos", grupoService.obtenerTodos());
    model.addAttribute("action", "view");
    return "asignacion/mant";
}
// ELIMINAR Confirmación
@GetMapping("/delete/{id}")
public String deleteConfirm(@PathVariable Integer id, Model model) {
    DocenteGrupo docenteGrupo = docenteGrupoService.buscarPorId(id);
    model.addAttribute("docenteGrupo", docenteGrupo);
    model.addAttribute("docentes", docenteService.obtenerTodos());
    model.addAttribute("grupos", grupoService.obtenerTodos());
    model.addAttribute("action", "delete");
    return "asignacion/mant";
}

//PROCESAR POST según action
@PostMapping("/create")
public String saveNuevo(@ModelAttribute DocenteGrupo docenteGrupo, BindingResult result,
                        RedirectAttributes redirect,
                        Model model) {
    if (result.hasErrors()) {
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "create");
        return "asignacion/mant";
    }
    docenteGrupoService.crearOeditar(docenteGrupo);
    redirect.addFlashAttribute("msg", "Asignación guardada correctamente.");
    return "redirect:/asignaciones";
}

@PostMapping("/edit")
public String saveEditado(@ModelAttribute DocenteGrupo docenteGrupo, 
                       BindingResult result,
                       RedirectAttributes redirect,Model model) {
    if (result.hasErrors()) {
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "edit");
        return "asignacion/mant";
    }
    docenteGrupoService.crearOeditar(docenteGrupo);
    redirect.addFlashAttribute("msg", "Asignación actualizada correctamente.");
    return "redirect:/asignaciones";
  }

  @PostMapping("/delete")
  public String delete(@ModelAttribute DocenteGrupo docenteGrupo,
                       RedirectAttributes redirect) {
    docenteGrupoService.eliminarPorId(docenteGrupo.getId());
    redirect.addFlashAttribute("msg", "Asignación eliminada correctamente.");
    return "redirect:/asignaciones";
  }
}