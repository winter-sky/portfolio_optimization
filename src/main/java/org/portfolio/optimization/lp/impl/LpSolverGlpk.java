package org.portfolio.optimization.lp.impl;


import org.gnu.glpk.*;
import org.portfolio.optimization.POException;
import org.portfolio.optimization.lp.*;

import java.util.ArrayList;
import java.util.List;

public class LpSolverGlpk extends LpProblemSolverAdaptor {
    private List<LpProblemConstraint> constraints = new ArrayList<>();

    private double[] coeff = null;

    private TargetDirection dir = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConstraint(LpProblemConstraint constraint) throws LpException {
        constraints.add(constraint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObjective(double[] coeff, TargetDirection dir) throws LpException {
        this.coeff = coeff;
        this.dir = dir;
    }

    /**
     * @param constraint
     * @return TBD
     */
    private static int getGlpkConstraint(LpProblemConstraint constraint) {
        switch (constraint.getRelation()) {
            case EQ: {
                return GLPKConstants.GLP_FX;
            }
            case GE: {
                return GLPKConstants.GLP_LO;
            }
            case LE: {
                return GLPKConstants.GLP_UP;
            }

            default: {
                throw new IllegalArgumentException("Invalid constraint [constraint=" + constraint + ']');
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LpProblemResult solve() throws POException {
        glp_prob lp = null;

        try {
            lp = GLPK.glp_create_prob();

            GLPK.glp_set_prob_name(lp, "glpk_problem");

            int rows = constraints.size();
            int cols = coeff.length;

            GLPK.glp_add_cols(lp, cols);

            for (int i = 1; i <= cols; i++) {
                String varName = "x" + i;

                GLPK.glp_set_col_name(lp, i, varName);
                GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_CV);
                GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_LO, 0, 0);
            }

            // Create rows
            GLPK.glp_add_rows(lp, rows);

            // Allocate memory
            SWIGTYPE_p_int ind = GLPK.new_intArray(rows);
            SWIGTYPE_p_double val = GLPK.new_doubleArray(rows);

            for (int row = 1; row <= rows; row++) {
                GLPK.glp_set_row_name(lp, row, "c" + row);

                LpProblemConstraint constraint = constraints.get(row - 1);

                double constraintValue = constraint.getValue();
                double[] coeffs = constraint.getCoeffs();

                GLPK.glp_set_row_bnds(lp, row, getGlpkConstraint(constraint), constraintValue, constraintValue);

                for (int col = 1; col <= cols; col++) {
                    double coeff = coeffs[col - 1];

                    GLPK.intArray_setitem(ind, col, col);
                    GLPK.doubleArray_setitem(val, col, coeff);
                }

                GLPK.glp_set_mat_row(lp, row, cols, ind, val);
            }

            GLPK.glp_set_obj_name(lp, "z");
            GLPK.glp_set_obj_dir(lp, dir == TargetDirection.MINIMUM ? GLPKConstants.GLP_MIN : GLPKConstants.GLP_MAX);

            for (int i = 1; i <= cols; i++) {
                GLPK.glp_set_obj_coef(lp, i, coeff[i - 1]);
            }

            // GLPK.glp_write_lp(lp, null, "c:/lp-complex.lp");

            glp_smcp parm = new glp_smcp();

            GLPK.glp_init_smcp(parm);

            parm.setMeth(GLPK.GLP_DUALP);
            parm.setMsg_lev(GLPK.GLP_MSG_ERR);

            int retCode = GLPK.glp_simplex(lp, parm);
            int statCode = GLPK.glp_get_status(lp);

            // if (ret == 0) {
            // write_lp_solution(lp);
            // }

            int m = GLPK.glp_get_num_cols(lp);
            double v = GLPK.glp_get_obj_val(lp);

            double[] res = new double[m];

            for (int i = 1; i <= m; i++) {
                res[i - 1] = GLPK.glp_get_col_prim(lp, i);
            }

            return new LpProblemResultImpl(getStatus(retCode, statCode), v, res);
        }
        finally {
            // Free memory
            if (lp != null) {
                GLPK.glp_delete_prob(lp);
            }

            constraints.clear();

            coeff = null;
            dir = null;
        }
    }

    /**
     * 
     * @param retCode
     * @param statCode
     * @return TBD
     */
    private static LpStatus getStatus(int retCode, int statCode) {
        if (retCode != 0) {
            if (retCode == GLPK.GLP_EBOUND) {
                return LpStatus.UNBOUND;
            }
            else if (retCode == GLPK.GLP_ENOPFS || retCode == GLPK.GLP_ENODFS) {
                return LpStatus.INFEASIBLE;
            }

            return LpStatus.OTHER;
        }

        if (statCode == GLPK.GLP_OPT) {
            return LpStatus.SUCCESS;
        }
        else if (statCode == GLPK.GLP_UNBND) {
            return LpStatus.UNBOUND;
        }
        else if (statCode == GLPK.GLP_INFEAS || statCode == GLPK.GLP_NOFEAS) {
            return LpStatus.INFEASIBLE;
        }

        return LpStatus.OTHER;
    }
}
