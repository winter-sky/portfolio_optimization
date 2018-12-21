package org.portfolio.optimization.lp.impl;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;
import org.portfolio.optimization.POException;
import org.portfolio.optimization.lp.*;

import java.util.Arrays;

/**
 * TBD: add comments for LpSolveLpProlemSolver.java.
 */
public class LpSolveLpProblemSolver extends LpProblemSolverAdaptor {
    /** */
    private final LpSolve solver;

    /** */
    private int verbose = 1;

    /**
     * 
     * TBD
     * 
     * @param size
     * @param verbose
     * @throws LpException
     */
    public LpSolveLpProblemSolver(int size, int verbose) throws POException {
        try {
            solver = LpSolve.makeLp(0, size);

            LpSolveConfig lc = LpConfig.getConfig().getLpSolveConfig();

            if (lc.getEspb() != null) {
                solver.setEpsb(lc.getEspb());
            }

            if (lc.getEspd() != null) {
                solver.setEpsd(lc.getEspd());
            }

            if (lc.getEspel() != null) {
                solver.setEpsel(lc.getEspel());
            }

            if (lc.getEsplevel() != null) {
                solver.setEpslevel(lc.getEsplevel());
            }

            this.verbose = verbose;
        }
        catch (LpSolveException e) {
            throw new POException("Cannot create problem [size=" + size + ']');
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConstraint(LpProblemConstraint constraint) throws LpException {
        assert constraint != null;

        try {
            solver.addConstraint(createRow(constraint.getCoeffs()), getRelationship(constraint.getRelation()),
                constraint.getValue());

            printConstraint(constraint);
        }
        catch (LpSolveException e) {
            throw new LpException("Error adding constraint [constraint=" + constraint + ']', e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObjective(double[] coeff, TargetDirection dir) throws LpException {
        if (dir == TargetDirection.MAXIMUM) {
            solver.setMaxim();
        }
        else {
            solver.setMinim();
        }

        // Set objective function.
        try {
            solver.setObjFn(createRow(coeff));
        }
        catch (LpSolveException e) {
            throw new LpException("Error setting objective function [coeff=" + Arrays.toString(coeff) + ']', e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LpProblemResult solve() throws LpException {
        int code;

        solver.setVerbose(verbose);

        try {
            code = solver.solve();
        }
        catch (LpSolveException e) {
            throw new LpException("Error while solving task.", e);
        }

        try {
            return new LpProblemResultImpl(getStatus(code), solver.getObjective(), solver.getPtrVariables());
        }
        catch (LpSolveException e) {
            throw new LpException("Error while getting results.", e);
        }
    }

    /**
     * 
     * TBD
     * 
     * @param relation
     * @return TBD
     * @throws LpException
     */
    private static int getRelationship(Relation relation) throws LpException {
        switch (relation) {
            case EQ: {
                return LpSolve.EQ;
            }

            case GE: {
                return LpSolve.GE;
            }

            case LE: {
                return LpSolve.LE;
            }

            default: {
                throw new LpException("Cannot convert relation [relation=" + relation + ']');
            }
        }
    }

    /**
     * 
     * TBD
     * 
     * @param code
     * @return TBD
     * @throws LpException
     */
    private static LpStatus getStatus(int code) throws LpException {
        switch (code) {
            case 0: {
                return LpStatus.SUCCESS;
            }

            case 2: {
                return LpStatus.INFEASIBLE;
            }

            case 3: {
                return LpStatus.UNBOUND;
            }

            default: {
                return LpStatus.OTHER;
            }
        }
    }

    /**
     * TODO: for some reason, Lp solve require to create row like this (with adding 1st element) while representing them
     * as an array of doubles. For string representation this is not requried. Investigate.
     * 
     * TBD
     * 
     * @param coeff
     * @return TBD
     */
    private double[] createRow(double[] coeff) {
        double[] row = new double[coeff.length + 1];

        row[0] = 0;

        System.arraycopy(coeff, 0, row, 1, coeff.length);

        return row;
    }
}
