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
@RequestMapping("/asignacion_estudiante")
public class EstudianteGrupoController {

    @Autowired
    private IEstudianteGrupoService estudianteGrupoService;

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private IEstudianteService estudianteService;

    // LISTADO
    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1; // PageRequest usa índice base 0
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<EstudianteGrupo> asignaciones = estudianteGrupoService.buscarTodosPaginados(pageable);
        model.addAttribute("asignaciones", asignaciones);

        int totalPages = asignaciones.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "asignacion_estudiante/index";                         
    }

    // CREAR
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("estudianteGrupo", new EstudianteGrupo());
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "create");
        return "asignacion_estudiante/mant";
    }

    // EDITAR
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        EstudianteGrupo estudianteGrupo = estudianteGrupoService.buscarPorId(id);
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "edit");
        return "asignacion_estudiante/mant";
    }

    // VISTA
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        EstudianteGrupo estudianteGrupo = estudianteGrupoService.buscarPorId(id);
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "view");
        return "asignacion_estudiante/mant";
    }

    // ELIMINAR Confirmación
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        EstudianteGrupo estudianteGrupo = estudianteGrupoService.buscarPorId(id);
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        model.addAttribute("grupos", grupoService.obtenerTodos());
        model.addAttribute("action", "delete");
        return "asignacion_estudiante/mant";
    }

    // PROCESAR POST según action
    @PostMapping("/create")
    public String saveNuevo(@ModelAttribute EstudianteGrupo estudianteGrupo, BindingResult result,
                            RedirectAttributes redirect,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteService.obtenerTodos());
            model.addAttribute("grupos", grupoService.obtenerTodos());
            model.addAttribute("action", "create");
            return "asignacion_estudiante/mant";
        }
        estudianteGrupoService.crearOeditar(estudianteGrupo);
        redirect.addFlashAttribute("msg", "Estudiante asignado correctamente al grupo.");
        return "redirect:/asignacion_estudiante";
    }

    @PostMapping("/edit")
    public String saveEditado(@ModelAttribute EstudianteGrupo estudianteGrupo, 
                              BindingResult result,
                              RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estudiantes", estudianteService.obtenerTodos());
            model.addAttribute("grupos", grupoService.obtenerTodos());
            model.addAttribute("action", "edit");
            return "asignacion_estudiante/mant";
        }
        estudianteGrupoService.crearOeditar(estudianteGrupo);
        redirect.addFlashAttribute("msg", "Asignación de estudiante actualizada correctamente.");
        return "redirect:/asignacion_estudiante";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute EstudianteGrupo estudianteGrupo,
                         RedirectAttributes redirect) {
        estudianteGrupoService.eliminarPorId(estudianteGrupo.getId());
        redirect.addFlashAttribute("msg", "Asignación de estudiante eliminada correctamente.");
        return "redirect:/asignacion_estudiante";
    }
}
